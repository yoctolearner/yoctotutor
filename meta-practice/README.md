# Yocto Project – Professional Build, Layer, and Recipe Guide

## 1. Introduction

The **Yocto Project** is an open-source framework used to create customized Linux-based operating-system images for embedded systems.

Yocto does not directly provide a Linux distribution. Instead, it provides the tools and metadata required to generate a customized Linux system for a specific target platform.

The major components involved in a Yocto build include:

- **BitBake** – The build engine used to execute recipes and tasks.
- **Poky** – A reference Yocto distribution containing OpenEmbedded build tools and metadata.
- **Layers** – Collections of related metadata.
- **Recipes (`.bb`)** – Instructions describing how software should be fetched, configured, compiled, installed, and packaged.
- **Configuration files** – Define machine, distribution, build, and layer configuration.
- **Classes (`.bbclass`)** – Reusable build logic for common build systems such as CMake and Autotools.

A simplified Yocto architecture is:

```text
                         Yocto Project
                              |
             +----------------+----------------+
             |                |                |
             v                v                v
           Layers          BitBake          Configuration
             |                |                |
             v                v                v
          Recipes           Tasks        local.conf
          Classes                         bblayers.conf
             |
             v
       Source Code
             |
             v
       Build / Compile
             |
             v
        Packages
             |
             v
       Root Filesystem
             |
             v
        Final Image
```

---

# 2. Yocto Project Directory Structure

A typical Yocto project may look like:

```text
yocto-project/
│
├── sources/
│   ├── poky/
│   ├── meta-openembedded/
│   ├── meta-freescale/
│   └── ...
│
├── meta-custom/
│
└── build/
    └── conf/
        ├── local.conf
        └── bblayers.conf
```

The important directories are:

### `sources/`

Contains the Yocto source repositories and layers.

Examples:

```text
sources/poky/
sources/meta-openembedded/
sources/meta-freescale/
```

### `meta-custom/`

Contains your own custom recipes, configuration, patches, device-tree changes, applications, etc.

### `build/`

Contains build-specific configuration and generated build output.

Important files:

```text
build/conf/local.conf
build/conf/bblayers.conf
```

---

# 3. Initializing the Yocto Build Environment

Before executing most BitBake commands, the Yocto build environment must be initialized.

Use:

```bash
source sources/poky/oe-init-build-env <build-directory>
```

For example:

```bash
source sources/poky/oe-init-build-env build-new
```

This script:

1. Sets up the required environment variables.
2. Adds BitBake utilities to the `PATH`.
3. Creates the build directory if necessary.
4. Creates the `conf` directory.
5. Changes the current working directory to the build directory.

After executing the command:

```bash
pwd
```

may show:

```text
/home/user/yocto-project/build-new
```

The build directory contains:

```text
build-new/
└── conf/
    ├── local.conf
    └── bblayers.conf
```

---

# 4. `local.conf`

The file:

```text
build/conf/local.conf
```

contains configuration specific to the current build.

Typical settings include:

```bitbake
MACHINE = "qemux86-64"
```

Package installation:

```bitbake
IMAGE_INSTALL:append = " python3 git"
```

Parallel build configuration:

```bitbake
BB_NUMBER_THREADS = "8"
PARALLEL_MAKE = "-j 8"
```

Other build-specific settings may also be defined here.

### Important principle

`local.conf` answers:

> **"How should this particular Yocto build be configured?"**

---

# 5. `bblayers.conf`

The file:

```text
build/conf/bblayers.conf
```

defines the layers that BitBake should use.

Example:

```bitbake
BBLAYERS ?= " \
    /home/user/yocto/sources/poky/meta \
    /home/user/yocto/sources/poky/meta-poky \
    /home/user/yocto/sources/meta-openembedded/meta-oe \
    /home/user/yocto/meta-custom \
"
```

### Important principle

`bblayers.conf` answers:

> **"Where should BitBake find the metadata used for this build?"**

---

# 6. Creating a Custom Layer

A custom layer is normally created to maintain project-specific metadata separately from the upstream Yocto layers.

Create a layer using:

```bash
bitbake-layers create-layer meta-<layer-name>
```

Example:

```bash
bitbake-layers create-layer meta-custom
```

A generated layer may initially look like:

```text
meta-custom/
├── conf/
│   └── layer.conf
├── recipes-example/
│   └── example/
│       └── example_0.1.bb
├── COPYING.MIT
└── README
```

For a real project, the layer can later be organized into:

```text
meta-custom/
├── conf/
├── recipes-apps/
├── recipes-core/
├── recipes-bsp/
├── recipes-kernel/
├── recipes-support/
└── README.md
```

---

# 7. Adding a Custom Layer

After creating the layer, add it to the current build:

```bash
bitbake-layers add-layer ../meta-custom
```

The command updates:

```text
build/conf/bblayers.conf
```

Verify the layer:

```bash
bitbake-layers show-layers
```

Example:

```text
layer                 path                                      priority
========================================================================
core                  /home/user/yocto/sources/poky/meta        5
poky                  /home/user/yocto/sources/poky/meta-poky   5
meta-oe               /home/user/yocto/sources/meta-openembedded/meta-oe 6
meta-custom           /home/user/yocto/meta-custom              7
```

---

# 8. Layer Priority

Layers can have priorities.

For example:

```text
meta                    priority 5
meta-oe                 priority 6
meta-custom             priority 7
```

When multiple layers provide the same recipe or metadata, layer priority can influence which metadata takes precedence.

However, **recipe version selection and `.bbappend` behavior involve additional BitBake rules**, so layer priority should not be considered the only mechanism controlling recipe selection.

For example, if you want to customize an existing recipe, a `.bbappend` is often the correct mechanism.

---

# 9. Recipe Fundamentals

A BitBake recipe normally has the extension:

```text
.bb
```

Example:

```text
hello_1.0.bb
```

A recipe describes:

```text
Source
  |
  v
Fetch
  |
  v
Unpack
  |
  v
Patch
  |
  v
Configure
  |
  v
Compile
  |
  v
Install
  |
  v
Package
```

The recipe does not necessarily implement every task manually.

Yocto provides classes and default task implementations for common build systems.

---

# 10. Recipe Naming Convention

A recipe commonly follows:

```text
<package-name>_<version>.bb
```

Example:

```text
hello_1.0.bb
```

Here:

```text
hello
```

is the package name.

```text
1.0
```

is the recipe version.

BitBake commonly derives:

```bitbake
PN = "hello"
PV = "1.0"
```

where:

- `PN` = Package Name
- `PV` = Package Version

---

# 11. Important Recipe Variables

A professional recipe commonly contains variables such as:

```bitbake
SUMMARY = "Short package description"
DESCRIPTION = "Detailed package description"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "..."

SRCREV = "..."

S = "${WORKDIR}/..."
```

The exact variables required depend on how the source is obtained and how the project is built.

---

# 12. `SUMMARY` and `DESCRIPTION`

### `SUMMARY`

A short description of the software:

```bitbake
SUMMARY = "Example embedded application"
```

### `DESCRIPTION`

A more detailed description:

```bitbake
DESCRIPTION = "Example application used for demonstrating Yocto recipe development"
```

---

# 13. `LICENSE`

The `LICENSE` variable specifies the software license.

Examples:

```bitbake
LICENSE = "MIT"
```

```bitbake
LICENSE = "GPL-2.0-only"
```

```bitbake
LICENSE = "BSD-3-Clause"
```

For proprietary source where appropriate:

```bitbake
LICENSE = "CLOSED"
```

The license should always be determined from the actual source project rather than guessed.

---

# 14. `LIC_FILES_CHKSUM`

For open-source software, Yocto commonly verifies the license file.

Example:

```bitbake
LIC_FILES_CHKSUM = "file://COPYING;md5=<checksum>"
```

First identify the license file:

```text
COPYING
LICENSE
LICENSE.txt
LICENSE.md
```

Then calculate its MD5 checksum:

```bash
md5sum COPYING
```

Example:

```text
d41d8cd98f00b204e9800998ecf8427e  COPYING
```

Then:

```bitbake
LIC_FILES_CHKSUM = "file://COPYING;md5=d41d8cd98f00b204e9800998ecf8427e"
```

The checksum allows BitBake to detect unexpected changes to the license text.

---

# 15. `SRC_URI`

`SRC_URI` specifies the source locations required by the recipe.

There are three common cases:

```text
1. Local files
2. Git repositories
3. Source archives
```

---

# 16. Local Source Files

If source files are stored along with the recipe, use:

```bitbake
SRC_URI = " \
    file://hello.c \
    file://hello.h \
    file://Makefile \
"
```

Typical directory structure:

```text
meta-custom/
└── recipes-apps/
    └── hello/
        ├── hello_1.0.bb
        └── files/
            ├── hello.c
            ├── hello.h
            └── Makefile
```

The `file://` URI tells BitBake to locate these files from the recipe's file search path.

---

# 17. Local Source Recipe Example

A simple application recipe:

```bitbake
SUMMARY = "Example local application"
DESCRIPTION = "Example application built from local source files"

LICENSE = "CLOSED"

SRC_URI = " \
    file://hello.c \
    file://Makefile \
"

S = "${WORKDIR}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hello ${D}${bindir}/hello
}
```

The important point is:

```text
${WORKDIR}
```

contains the files fetched/unpacked by BitBake.

Therefore:

```bitbake
S = "${WORKDIR}"
```

is appropriate when the source files are directly placed there.

---

# 18. `do_compile()`

The `do_compile()` task builds the application.

For a simple C program:

```bitbake
do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} hello.c -o hello
}
```

For a Makefile project:

```bitbake
do_compile() {
    oe_runmake
}
```

`oe_runmake` is preferred because it integrates the project's Makefile with the Yocto cross-compilation environment.

---

# 19. `do_install()`

The `do_install()` task installs files into `${D}`.

Example:

```bitbake
do_install() {
    install -d ${D}${bindir}
    install -m 0755 hello ${D}${bindir}/hello
}
```

Important:

```text
${D}
```

is **not the target root filesystem itself**.

It is a staging area used during packaging.

For example:

```text
${D}/usr/bin/hello
```

will eventually result in:

```text
/usr/bin/hello
```

inside the target root filesystem.

---

# 20. Installing Libraries

For a library, directories such as:

```bitbake
${libdir}
${includedir}
```

may be used.

Example:

```bitbake
do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    install -m 0644 libexample.so ${D}${libdir}/
    install -m 0644 example.h ${D}${includedir}/
}
```

The exact installation method depends on whether the library is static, shared, or both.

---

# 21. Git-Based Source

When the project is maintained in Git, BitBake can fetch the repository.

Example:

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"
```

The Git revision is specified using:

```bitbake
SRCREV = "<commit-id>"
```

Example:

```bitbake
SRCREV = "8c5f9c4d2a7b6e..."
```

For a Git source, the source directory is commonly:

```bitbake
S = "${WORKDIR}/git"
```

---

# 22. Finding the Git URL

If the project already exists locally:

```bash
cd <project-directory>
```

Run:

```bash
git remote -v
```

Example:

```text
origin  https://github.com/example/project.git (fetch)
origin  https://github.com/example/project.git (push)
```

The repository URL can then be used in:

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"
```

---

# 23. Finding the Git Branch

Use:

```bash
git branch -a
```

Example:

```text
* main
  remotes/origin/main
  remotes/origin/develop
```

The branch can be specified using:

```bitbake
branch=main
```

For example:

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"
```

---

# 24. Finding `SRCREV`

`SRCREV` identifies the exact Git revision to build.

Use:

```bash
git rev-parse HEAD
```

Example:

```text
8c5f9c4d2a7b6e1234567890abcdef...
```

Then:

```bitbake
SRCREV = "8c5f9c4d2a7b6e1234567890abcdef..."
```

Using a fixed commit improves build reproducibility.

---

# 25. Git Recipe Example

A complete example:

```bitbake
SUMMARY = "Example Git application"
DESCRIPTION = "Application fetched from a Git repository"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hello ${D}${bindir}/hello
}
```

---

# 26. `nobranch=1`

Sometimes a recipe uses:

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;nobranch=1"
```

This is useful when the recipe wants to fetch a particular revision without requiring that revision to be associated with a configured branch.

For normal branch-based development, it is generally clearer to use:

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"
```

together with:

```bitbake
SRCREV = "<commit-id>"
```

---

# 27. HTTPS Source Archives

A project may be distributed as:

```text
.tar.gz
.tar.bz2
.tar.xz
```

Example:

```bitbake
SRC_URI = "https://example.com/download/project-1.0.tar.gz"
```

BitBake downloads and extracts the archive during the fetch/unpack stages.

---

# 28. SHA-256 Verification

For remote source archives, specify the checksum:

```bitbake
SRC_URI[sha256sum] = "<sha256-value>"
```

Download the file manually:

```bash
wget https://example.com/download/project-1.0.tar.gz
```

Calculate:

```bash
sha256sum project-1.0.tar.gz
```

Example:

```text
8f5d7a3b1234... project-1.0.tar.gz
```

Then:

```bitbake
SRC_URI[sha256sum] = "8f5d7a3b1234..."
```

This ensures that the downloaded source matches the expected archive.

---

# 29. `S` for Source Archives

After an archive is extracted, the source may be located under:

```text
${WORKDIR}/project-1.0
```

In that case:

```bitbake
S = "${WORKDIR}/project-1.0"
```

The exact directory name depends on the archive contents.

You should verify the extracted directory rather than assuming its name.

---

# 30. CMake Projects

For a CMake project, use:

```bitbake
inherit cmake
```

Typical source tree:

```text
project/
├── CMakeLists.txt
├── src/
│   └── main.c
└── include/
    └── main.h
```

Recipe:

```bitbake
SUMMARY = "Example CMake application"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

inherit cmake
```

If the project's `CMakeLists.txt` contains appropriate installation rules, the recipe may not need a custom `do_install()`.

---

# 31. Makefile Projects

For projects using a traditional Makefile:

```bitbake
do_compile() {
    oe_runmake
}
```

Example:

```bitbake
SUMMARY = "Example Makefile application"

LICENSE = "MIT"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hello ${D}${bindir}/hello
}
```

---

# 32. Autotools Projects

Projects using GNU Autotools commonly contain:

```text
configure.ac
Makefile.am
```

For these projects:

```bitbake
inherit autotools
```

If the project uses `pkg-config`:

```bitbake
inherit autotools pkgconfig
```

Example:

```bitbake
SUMMARY = "Example Autotools project"

LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://COPYING;md5=<checksum>"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
```

The `autotools` class handles the standard configure/build/install integration.

---

# 33. Why Use `inherit`?

Instead of manually implementing:

```text
configure
make
make install
```

Yocto provides reusable classes.

Examples:

```bitbake
inherit cmake
```

```bitbake
inherit autotools
```

```bitbake
inherit pkgconfig
```

This reduces recipe complexity and makes recipes more consistent.

---

# 34. Package Installation into an Image

There are several ways to add packages to an image.

For a quick build-specific configuration, `local.conf` can contain:

```bitbake
IMAGE_INSTALL:append = " \
    python3 \
    git \
    vim \
"
```

This adds the packages to the image.

However, for a maintainable product, it is often better to create a **custom image recipe** rather than putting large amounts of product configuration into `local.conf`.

For example:

```text
meta-custom/
└── recipes-core/
    └── images/
        └── custom-image.bb
```

Then:

```bitbake
DESCRIPTION = "Custom embedded Linux image"

inherit core-image

IMAGE_INSTALL += " \
    python3 \
    git \
    my-application \
"
```

Build it with:

```bash
bitbake custom-image
```

This approach is more suitable for production projects.

---

# 35. `IMAGE_INSTALL:append` vs `IMAGE_INSTALL +=`

Both can be encountered in Yocto configurations.

For example:

```bitbake
IMAGE_INSTALL:append = " python3"
```

or:

```bitbake
IMAGE_INSTALL += "python3"
```

When modifying an existing variable, be aware that BitBake's override and operator semantics matter.

For a simple addition in `local.conf`, the commonly used form is:

```bitbake
IMAGE_INSTALL:append = " python3 git"
```

Always include the leading space when using `:append`.

---

# 36. Package vs Recipe

These terms are related but different.

### Recipe

A `.bb` file describing how software is built.

Example:

```text
hello_1.0.bb
```

### Package

The output produced from the recipe.

For example:

```text
hello
hello-dev
hello-dbg
```

A single recipe can generate multiple packages.

Therefore:

```text
Recipe
   |
   +----> runtime package
   |
   +----> development package
   |
   +----> debug package
```

---

# 37. `PN`, `PV`, and `PR`

Some important BitBake variables are:

### `PN`

Package name:

```bitbake
PN = "hello"
```

### `PV`

Package version:

```bitbake
PV = "1.0"
```

### `PR`

Recipe revision.

Historically recipes may contain:

```bitbake
PR = "r1"
```

Modern Yocto workflows generally avoid manually incrementing `PR` unless there is a specific reason.

---

# 38. Important Build Directories

Several BitBake variables are particularly important when debugging recipes.

## `WORKDIR`

Recipe-specific working directory.

```text
${WORKDIR}
```

It contains temporary files, fetched sources, logs, patches, and other build data.

---

## `S`

Source directory:

```bitbake
S = "${WORKDIR}/git"
```

---

## `B`

Build directory.

For out-of-tree builds:

```text
S != B
```

For example:

```text
S = ${WORKDIR}/git
B = ${WORKDIR}/build
```

---

## `D`

Destination staging directory:

```text
${D}
```

Files installed here are subsequently packaged.

---

# 39. Understanding `S`, `B`, `D`, and `WORKDIR`

These four variables are extremely important.

```text
                 WORKDIR
                    |
          +---------+---------+
          |                   |
          v                   v
       Source                 Build
          |                   |
          S                   B
          |                   |
          +---------+---------+
                    |
                    v
               do_install
                    |
                    v
                    D
                    |
                    v
                Packaging
```

For example:

```text
WORKDIR
├── git/             <-- S
├── build/           <-- B
├── image/           <-- D
└── temp/
```

---

# 40. BitBake Tasks

Common tasks include:

```text
do_fetch
do_unpack
do_patch
do_configure
do_compile
do_install
do_package
do_package_qa
```

A simplified execution flow:

```text
do_fetch
   |
   v
do_unpack
   |
   v
do_patch
   |
   v
do_configure
   |
   v
do_compile
   |
   v
do_install
   |
   v
do_package
   |
   v
do_package_qa
```

Not every recipe manually implements these tasks.

Yocto classes and BitBake provide many of them automatically.

---

# 41. Inspecting BitBake Environment Variables

The command:

```bash
bitbake -e <recipe>
```

prints the environment after parsing configuration, classes, recipes, and overrides.

Because the output is very large, use `grep`.

Example:

```bash
bitbake -e libiec61850 | grep '^PN='
```

Output:

```text
PN="libiec61850"
```

---

# 42. Inspecting Multiple Variables

Use:

```bash
bitbake -e libiec61850 | \
grep -E '^(S|B|WORKDIR|D|SRC_URI|PV|PN|FILESEXTRAPATHS|FILES)='
```

This is useful when debugging:

- Source location
- Build location
- Installation location
- Recipe name
- Version
- Source URI
- File search paths
- Package file lists

---

# 43. Useful Variable Inspection Commands

### Package name

```bash
bitbake -e <recipe> | grep '^PN='
```

### Version

```bash
bitbake -e <recipe> | grep '^PV='
```

### Source directory

```bash
bitbake -e <recipe> | grep '^S='
```

### Build directory

```bash
bitbake -e <recipe> | grep '^B='
```

### Working directory

```bash
bitbake -e <recipe> | grep '^WORKDIR='
```

### Source URI

```bash
bitbake -e <recipe> | grep '^SRC_URI='
```

### Destination directory

```bash
bitbake -e <recipe> | grep '^D='
```

---

# 44. Useful Recipe Debugging Commands

### Show recipe information

```bash
bitbake-layers show-recipes <recipe>
```

Example:

```bash
bitbake-layers show-recipes libiec61850
```

This helps identify which layers provide the recipe.

---

### Show recipe append files

```bash
bitbake-layers show-appends <recipe>
```

This is useful when debugging `.bbappend` files.

---

### Build a recipe

```bash
bitbake <recipe>
```

Example:

```bash
bitbake libiec61850
```

---

### Clean a recipe

```bash
bitbake -c clean <recipe>
```

---

### Clean and remove downloaded source

```bash
bitbake -c cleansstate <recipe>
```

Use `cleansstate` carefully because it removes shared-state information for that recipe.

---

### Run a specific task

```bash
bitbake -c compile <recipe>
```

or:

```bash
bitbake -c install <recipe>
```

---

# 45. Examining the Recipe's Work Directory

After building a recipe, the work directory can be found using:

```bash
bitbake -e <recipe> | grep '^WORKDIR='
```

Example:

```text
WORKDIR="/home/user/yocto/build/tmp/work/armv7at2hf-neon-poky-linux-gnueabi/hello/1.0"
```

You can then inspect:

```bash
cd /home/user/yocto/build/tmp/work/.../hello/1.0/
```

Important directories may include:

```text
git/
build/
image/
temp/
packages-split/
```

---

# 46. Inspecting Task Logs

If a task fails, BitBake normally provides a log file.

For example:

```text
temp/log.do_compile.*
```

You can locate it using:

```bash
find tmp/work -name "log.do_compile*"
```

Then inspect it:

```bash
less <log-file>
```

This is one of the most important techniques for debugging Yocto build failures.

---

# 47. Recipe Development Workflow

A professional workflow for adding a new application is:

```text
1. Obtain source code
       |
       v
2. Determine build system
       |
       +---- CMake
       |
       +---- Makefile
       |
       +---- Autotools
       |
       +---- Custom build
       |
       v
3. Determine license
       |
       v
4. Create custom layer
       |
       v
5. Create recipe
       |
       v
6. Define SRC_URI
       |
       v
7. Define SRCREV/checksum
       |
       v
8. Define S
       |
       v
9. Select appropriate inherit class
       |
       v
10. Build recipe
       |
       v
11. Debug
       |
       v
12. Add package to image
       |
       v
13. Build final image
```

---

# 48. Complete Example – Git + CMake

Assume the project has:

```text
Git repository
    |
    +-- CMakeLists.txt
    +-- src/
    +-- include/
    +-- LICENSE
```

The recipe can be:

```bitbake
SUMMARY = "Example CMake application"
DESCRIPTION = "Example embedded application"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

inherit cmake
```

Build:

```bash
bitbake example
```

If successful, add it to the image:

```bitbake
IMAGE_INSTALL:append = " example"
```

Then build:

```bash
bitbake core-image-minimal
```

---

# 49. Complete Example – Local Source + Makefile

Directory:

```text
meta-custom/
└── recipes-apps/
    └── hello/
        ├── hello_1.0.bb
        └── files/
            ├── hello.c
            └── Makefile
```

Recipe:

```bitbake
SUMMARY = "Hello application"

DESCRIPTION = "Simple Hello World application"

LICENSE = "CLOSED"

SRC_URI = " \
    file://hello.c \
    file://Makefile \
"

S = "${WORKDIR}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hello ${D}${bindir}/hello
}
```

Build:

```bash
bitbake hello
```

---

# 50. Complete Example – HTTPS Tarball

```bitbake
SUMMARY = "Example source archive application"

DESCRIPTION = "Application downloaded from an HTTPS source archive"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "https://example.com/download/example-1.0.tar.gz"
SRC_URI[sha256sum] = "<sha256-checksum>"

S = "${WORKDIR}/example-1.0"

inherit cmake
```

The archive is:

```text
download
   |
   v
verify SHA256
   |
   v
extract
   |
   v
configure
   |
   v
compile
   |
   v
install
   |
   v
package
```

---

# 51. Local Files vs Git vs HTTPS

| Source Type | `SRC_URI` | Revision / Verification |
|---|---|---|
| Local files | `file://...` | Local source |
| Git repository | `git://...` | `SRCREV` |
| HTTPS archive | `https://...` | `SRC_URI[sha256sum]` |

### Local source

```bitbake
SRC_URI = "file://hello.c"
```

### Git source

```bitbake
SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"
SRCREV = "<commit>"
```

### HTTPS archive

```bitbake
SRC_URI = "https://example.com/project.tar.gz"
SRC_URI[sha256sum] = "<checksum>"
```

---

# 52. Recommended Professional Layer Structure

For a production embedded Linux project, a custom layer may be organized as:

```text
meta-custom/
│
├── conf/
│   └── layer.conf
│
├── recipes-apps/
│   ├── application1/
│   │   └── application1_1.0.bb
│   │
│   └── application2/
│       └── application2_1.0.bb
│
├── recipes-core/
│   ├── images/
│   │   └── custom-image.bb
│   │
│   └── packagegroups/
│       └── packagegroup-custom.bb
│
├── recipes-bsp/
│   └── u-boot/
│       └── u-boot-custom.bbappend
│
├── recipes-kernel/
│   └── linux/
│       ├── linux-custom.bbappend
│       └── linux-custom/
│           ├── 0001-custom.patch
│           └── custom-device-tree.dts
│
└── README.md
```

This organization keeps application, BSP, kernel, and image metadata separated.

---

# 53. Best Practices

## 53.1 Do not modify upstream layers unnecessarily

Avoid directly modifying:

```text
poky/meta/
meta-openembedded/
meta-freescale/
```

Instead, create your own layer:

```text
meta-custom/
```

and use:

```text
.bbappend
```

files where appropriate.

---

## 53.2 Pin Git revisions

Prefer:

```bitbake
SRCREV = "<specific-commit>"
```

rather than relying on a moving branch head.

This improves reproducibility.

---

## 53.3 Verify source archives

For downloaded archives, specify:

```bitbake
SRC_URI[sha256sum] = "..."
```

---

## 53.4 Use the appropriate build class

Use:

```bitbake
inherit cmake
```

for CMake projects.

Use:

```bitbake
inherit autotools
```

for Autotools projects.

Use:

```bitbake
inherit pkgconfig
```

when appropriate.

Avoid unnecessarily implementing standard build logic manually.

---

## 53.5 Keep product configuration out of `local.conf`

For experimentation:

```bitbake
IMAGE_INSTALL:append = " python3"
```

in `local.conf` is convenient.

For production, prefer a custom image or packagegroup:

```text
meta-custom/
└── recipes-core/
    ├── images/
    └── packagegroups/
```

This makes the configuration version-controlled and reproducible.

---

# 54. Common Mistakes

### Mistake 1 – Forgetting the leading space

Incorrect:

```bitbake
IMAGE_INSTALL:append = "python3"
```

Preferred:

```bitbake
IMAGE_INSTALL:append = " python3"
```

---

### Mistake 2 – Incorrect Git revision

If:

```bitbake
SRCREV = "<invalid-commit>"
```

BitBake cannot fetch the requested source revision.

Verify using:

```bash
git rev-parse HEAD
```

---

### Mistake 3 – Incorrect `S`

If the extracted source directory is:

```text
${WORKDIR}/project-1.0
```

but the recipe says:

```bitbake
S = "${WORKDIR}/project"
```

the build can fail because BitBake cannot find the source.

---

### Mistake 4 – Missing installation

Compilation succeeding does not automatically mean the application will appear in the final image.

The recipe must install the required files:

```bitbake
do_install() {
    install -d ${D}${bindir}
    install -m 0755 hello ${D}${bindir}/hello
}
```

---

### Mistake 5 – Package not included in the image

Building:

```bash
bitbake hello
```

does not automatically mean `hello` is included in your final image.

You must add it to the image, for example:

```bitbake
IMAGE_INSTALL:append = " hello"
```

or through a custom image/packagegroup.

---

# 55. Useful Command Reference

## Environment

```bash
source sources/poky/oe-init-build-env build
```

## Layers

```bash
bitbake-layers create-layer meta-custom
```

```bash
bitbake-layers add-layer ../meta-custom
```

```bash
bitbake-layers show-layers
```

```bash
bitbake-layers show-recipes
```

```bash
bitbake-layers show-appends
```

## Build

```bash
bitbake <recipe>
```

```bash
bitbake <image>
```

## Tasks

```bash
bitbake -c fetch <recipe>
```

```bash
bitbake -c unpack <recipe>
```

```bash
bitbake -c configure <recipe>
```

```bash
bitbake -c compile <recipe>
```

```bash
bitbake -c install <recipe>
```

## Clean

```bash
bitbake -c clean <recipe>
```

```bash
bitbake -c cleansstate <recipe>
```

## Environment inspection

```bash
bitbake -e <recipe>
```

```bash
bitbake -e <recipe> | grep '^PN='
```

```bash
bitbake -e <recipe> | grep '^PV='
```

```bash
bitbake -e <recipe> | grep '^S='
```

```bash
bitbake -e <recipe> | grep '^B='
```

```bash
bitbake -e <recipe> | grep '^WORKDIR='
```

```bash
bitbake -e <recipe> | grep '^SRC_URI='
```

---

# 56. Quick Reference – Recipe Template

## Local Source

```bitbake
SUMMARY = "Package summary"
DESCRIPTION = "Package description"

LICENSE = "CLOSED"

SRC_URI = " \
    file://source.c \
    file://Makefile \
"

S = "${WORKDIR}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/application ${D}${bindir}/application
}
```

---

## Git Source

```bitbake
SUMMARY = "Package summary"
DESCRIPTION = "Package description"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "git://github.com/example/project.git;protocol=https;branch=main"

SRCREV = "<commit-id>"

S = "${WORKDIR}/git"

inherit cmake
```

---

## HTTPS Source Archive

```bitbake
SUMMARY = "Package summary"
DESCRIPTION = "Package description"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE;md5=<checksum>"

SRC_URI = "https://example.com/project-1.0.tar.gz"
SRC_URI[sha256sum] = "<sha256-checksum>"

S = "${WORKDIR}/project-1.0"

inherit cmake
```

---

# 57. Final Concept

The most important concept to understand is the relationship between **Layer → Recipe → Source → Tasks → Package → Image**.

```text
                 CUSTOM LAYER
                      |
                      v
                   RECIPE
                 (.bb file)
                      |
          +-----------+-----------+
          |           |           |
          v           v           v
       Local         Git       HTTPS
       Source       Source     Archive
          |           |           |
          +-----------+-----------+
                      |
                      v
                  do_fetch
                      |
                      v
                 do_unpack
                      |
                      v
                  do_patch
                      |
                      v
                do_configure
                      |
                      v
                 do_compile
                      |
                      v
                 do_install
                      |
                      v
                 do_package
                      |
                      v
                 PACKAGE
                      |
                      v
              IMAGE_INSTALL
                      |
                      v
                ROOTFS
                      |
                      v
                FINAL IMAGE
                      |
                      v
              Target Hardware
```

### Key takeaway

A **Yocto layer** organizes metadata.

A **BitBake recipe** describes how a particular software component is obtained and built.

`SRC_URI` tells BitBake **where the source comes from**.

`SRCREV` identifies **which Git revision should be built**.

`SRC_URI[sha256sum]` verifies **the integrity of a source archive**.

`S` identifies **where the source is located**.

`B` identifies **where the build occurs**.

`D` identifies **the installation staging directory**.

`do_compile()` builds the software.

`do_install()` stages the files for packaging.

`inherit cmake`, `inherit autotools`, etc. provide reusable build-system integration.

Finally, the generated package must be included in an image before it becomes part of the target root filesystem.