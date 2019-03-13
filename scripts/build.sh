#!/usr/bin/env bash

# common stuff
isPullRequest() {
    [[ "$TRAVIS_PULL_REQUEST" == "false" ]] && return 1 || return 0
}

exitOnFailedBuild() {
    if [ $? -ne 0 ]; then
        exit 1
    fi
}

boxOut(){
    local s="$*"
    tput setaf 3
    echo -e " =${s//?/=}=\n| $(tput setaf 4)$s$(tput setaf 3) |\n =${s//?/=}=\n"
    tput sgr 0
}

acceptLicenses() {
    mkdir -p ${ANDROID_HOME}licenses
    echo -e "\nd56f5187479451eabf01fb78af6dfcb131a6481e\n24333f8a63b6825ea9c5514f83c2829b004d1fee" > ${ANDROID_HOME}licenses/android-sdk-license
}

buildPicassoCompat() {
    ./gradlew :picassocompat_parent:assembleRelease :picassocompat_252:assembleRelease :picassocompat_271828:assembleRelease :picassocompat:assembleRelease
    exitOnFailedBuild
}

buildSampleApp() {
    ./gradlew :app:assembleRelease
    exitOnFailedBuild
}

uploadSnapshot() {
    export LOCAL_BUILD="false"

    ./gradlew --settings-file scripts/gradle/build-picasso-compat-parent.gradle clean :picassocompat_parent:uploadArchive
    exitOnFailedBuild

    ./gradlew --settings-file scripts/gradle/build-picasso-compat-252.gradle clean :picassocompat_252:uploadArchive
    exitOnFailedBuild

    ./gradlew --settings-file scripts/gradle/build-picasso-compat-271828.gradle clean :picassocompat_271828:uploadArchive
    exitOnFailedBuild

    ./gradlew --settings-file scripts/gradle/build-picasso-compat.gradle clean :picassocompat:uploadArchive
    exitOnFailedBuild

    buildSampleApp
    exitOnFailedBuild

    unset LOCAL_BUILD
}

# Build types
pullRequestBuild() {
    export LOCAL_BUILD="true"

    boxOut "Build Picasso Compat"
    buildPicassoCompat

    boxOut "Build Sample App"
    buildSampleApp

    unset LOCAL_BUILD
}

branchBuild() {
    pullRequestBuild

    boxOut "Upload Snapshots"
    uploadSnapshot
}

acceptLicenses

# do the thing
if isPullRequest ; then
    boxOut "This is a PR"
    pullRequestBuild
else
    boxOut "This is not a PR"
    branchBuild
fi