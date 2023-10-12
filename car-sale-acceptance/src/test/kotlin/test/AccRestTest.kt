package test

import docker.WiremockDockerCompose
import fixture.BaseFunSpec
import fixture.client.RestClient
import fixture.docker.DockerCompose
import io.kotest.core.annotation.Ignored

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val client = RestClient(dockerCompose)

    testApiV1(client)
})
class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)