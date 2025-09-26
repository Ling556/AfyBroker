plugins {
    `java-library`
    `maven-publish`
    id("afybroker-publish")
}

dependencies {
    api(libs.hessian)
    api(libs.kryo)
    compileOnlyApi(libs.annotations)
    compileOnlyApi(libs.slf4j.api)
    compileOnly(libs.guava)
    compileOnly(libs.netty)
}

java {
    withSourcesJar()
}