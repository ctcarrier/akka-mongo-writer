package com.ctcarrier.schema

import com.ctcarrier.model.Widget
import org.squeryl.Schema
import com.ctcarrier.util.SquerylNamingConventionTransforms

/**
 * @author chris_carrier
 * @version 7/13/11
 */


object Library extends Schema {

  override def columnNameFromPropertyName(n:String) =
    SquerylNamingConventionTransforms.camelCase2lowerUnderScore(n)

  val widgets = table[Widget]("widget")
}