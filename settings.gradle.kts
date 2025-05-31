pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // 阿里云镜像
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        // 华为云镜像
        // maven { url = uri("https://repo.huaweicloud.com/repository/maven/") }
        // JitPack 仓库 (如果你需要从 GitHub 等直接构建的库)
        maven { url = uri("https://jitpack.io") }

    }
}

rootProject.name = "My Application0531"
include(":app")
 