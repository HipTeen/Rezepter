package data.unit

import com.sun.org.apache.xerces.internal.xni.grammars.Grammar

/**
 *
 */
class Gramm(amount: Int) extends QuantityUnit {
  override def getAmount: Int = amount

  override def toString: String = amount  + " Gramm"
}
