package io.slicker.postgres

import com.github.tminglei.slickpg._

trait PostgresDriver extends ExPostgresProfile
  with PgArraySupport
  with PgDate2Support
  with PgRangeSupport
  with PgHStoreSupport
  with PgSearchSupport
  with PgNetSupport
  with PgEnumSupport
  with PgLTreeSupport
  with PgCompositeSupport {

  override val api = new SlickAPI {}

  trait SlickAPI extends API with ArrayImplicits
    with DateTimeImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }

}

object PostgresDriver extends PostgresDriver