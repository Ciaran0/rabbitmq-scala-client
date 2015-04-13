package amqptest

import io.relayr.amqp.{ConnectionHolder, Event, EventHooks, ReconnectionStrategy}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.language.postfixOps

trait AMQPIntegrationFixtures extends BeforeAndAfterAll with EmbeddedAMQPBroker with MockFactory { this: Suite =>

  override def beforeAll() {
    initializeBroker()
  }

  def connection(eventListener: Event ⇒ Unit, reconnectionStrategy: ReconnectionStrategy) = ConnectionHolder.builder(amqpUri)
    .eventHooks(EventHooks(eventListener))
    .reconnectionStrategy(reconnectionStrategy)
    .build()

  trait ClientTestContext {
    def clientReconnectionStrategy: ReconnectionStrategy = ReconnectionStrategy.NoReconnect

    val clientEventListener = mockFunction[Event, Unit]

    val clientConnection: ConnectionHolder = connection(clientEventListener, clientReconnectionStrategy)
  }

  trait ServerTestContext {
    def serverReconnectionStrategy: ReconnectionStrategy = ReconnectionStrategy.NoReconnect

    val serverEventListener = mockFunction[Event, Unit]

    val serverConnection: ConnectionHolder = connection(serverEventListener, serverReconnectionStrategy)
  }


  override def afterAll() = {
    shutdownBroker()
  }
}
