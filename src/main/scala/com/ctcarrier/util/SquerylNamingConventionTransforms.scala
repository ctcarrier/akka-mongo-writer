package com.ctcarrier.util

/**
 * @author chris_carrier
 * @version 7/14/11
 */


object SquerylNamingConventionTransforms {

    def camelCase2lowerUnderScore(name: String) =
      name.toList.map(c => if(c.isUpper) "_" + c.toLower else c).mkString
  }