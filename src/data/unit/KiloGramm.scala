package data.unit

/**
 *
 */
case class KiloGramm(amount: Int) extends QuantityUnit {
  override def getAmount: Int = amount
}
