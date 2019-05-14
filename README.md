 <p align="center">
 <h1>Picasso Compat</h1>
 <a href="https://travis-ci.org/schlan/picassocompat"><img src="https://travis-ci.org/schlan/picassocompat.svg?branch=master" alt="Build Status" /></a><a href="https://repo.jfrog.org/artifactory/libs-release-bintray/com/sebchlan/picassocompat/picassocompat/">
 <img src="https://img.shields.io/maven-central/v/com.sebchlan.picassocompat/picassocompat.svg" alt="Picasso Compat Version" /></a>
 <a href="https://oss.sonatype.org/content/repositories/snapshots/com/sebchlan/picassocompat/picassocompat"> <img src="https://img.shields.io/nexus/s/https/oss.sonatype.org/com.sebchlan.picassocompat/picassocompat.svg" /></a>
 <a href="https://github.com/schlan/picassocompat/blob/master/LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License" /></a>
 </p>

 Provides a thin compatibility later around the Picasso 2.5.2 and 2.7 APIs. It's build for libraries that rely on Picasso. By using it, the host app can either pull in Picasso 2.5.2 or 2.7. PicassoCompat detects the version of Picasso and forwards all calls to it. With this, it wouldn't be necessary to have a compatibility release.

## Installation

```java
implementation group: 'com.sebchlan.picassocompat', name: 'picassocompat', version: '1.2.1'
```

By default PicassoCompat depends on Picasso `v2.5.2`.

## Usage

Like Picasso PicassoCompat provides two main entry points:

```java

// Shared Picasso instance
PicassoCompat init = PicassoBridge.init(context);

// Builder for creating a customized Picasso instance
PicassoCompat.Builder builder = PicassoBridge.builder(context);
```

The remaining usage is identical to OG Picasso.

Enjoy.
