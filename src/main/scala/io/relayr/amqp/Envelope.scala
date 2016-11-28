package io.relayr.amqp

/**
 * The message and any routing information that is included when it is received from a queue.
 * @param exchange
 * @param redeliver
 * @param deliveryTag
 * @param routingKey
 * @param message
 */
case class Envelope(exchange: String, redeliver: Boolean, deliveryTag: Long, routingKey: String, message: Message)
