package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class PlayLiquibasePersonService @Inject()(private val personDao: PlayLiquibasePersonDao) {
  def getAll: Seq[PlayLiquibasePerson] = Await.result(personDao.getAll, Duration.Inf)

  def create(person: PlayLiquibasePerson): Unit = Await.result(personDao.create(person), Duration.Inf)

  def delete(personId: Int): Unit = Await.result(personDao.delete(personId), Duration.Inf)
}
