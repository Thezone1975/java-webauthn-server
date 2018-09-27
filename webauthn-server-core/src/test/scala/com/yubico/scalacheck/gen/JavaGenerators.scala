package com.yubico.scalacheck.gen

import java.net.URL
import java.util.Optional

import com.yubico.internal.util.scala.JavaConverters._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary

import scala.collection.JavaConverters._


object JavaGenerators {

  implicit def arbitraryOptional[A](implicit a: Arbitrary[A]): Arbitrary[Optional[A]] = Arbitrary(Gen.option(a.arbitrary).map(_.asJava))

  implicit def arbitraryList[A](implicit a: Arbitrary[List[A]]): Arbitrary[java.util.List[A]] = Arbitrary(a.arbitrary map (_.asJava))
  implicit def arbitraryMap[A, B](implicit a: Arbitrary[Map[A, B]]): Arbitrary[java.util.Map[A, B]] = Arbitrary(a.arbitrary map (_.asJava))
  implicit def arbitrarySet[A](implicit a: Arbitrary[Set[A]]): Arbitrary[java.util.Set[A]] = Arbitrary(a.arbitrary map (_.asJava))

  implicit val arbitraryUrl: Arbitrary[URL] = Arbitrary(url())
  def url(
    scheme: Gen[String] = Gen.oneOf("http", "https"),
    host: Gen[String] = Gen.alphaNumStr,
    path: Gen[String] = Gen.alphaNumStr
  ): Gen[URL] = for {
    scheme <- scheme
    host <- host
    path <- path
  } yield new URL(s"${scheme}://${host}${if (path.isEmpty) "" else "/"}${path}")

  implicit val arbitraryLong: Arbitrary[java.lang.Long] = Arbitrary(arbitrary[Long].map((a: Long) => a))

}
