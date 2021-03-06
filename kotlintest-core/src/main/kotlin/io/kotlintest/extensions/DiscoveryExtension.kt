package io.kotlintest.extensions

import io.kotlintest.Spec
import kotlin.reflect.KClass

/**
 * Allows interception of the discovery phase of KotlinTest.
 *
 * The discovery phase is the part of the test cycle that finds
 * possible [Spec] classes on the classpath and then instantiates them
 * ready to be executed.
 *
 * Note: If multiple [DiscoveryExtension]s are registered, the order
 * in which they execute is not specified.
 */
interface DiscoveryExtension : ProjectLevelExtension {

  /**
   * Invoked as soon as the scan phase is complete. At that point,
   * the [Spec] classes have been detected, but not yet instantiated
   * or executed.
   *
   * Overriding this function gives implementations the possibility
   * of filtering the specs seen by the test engine.
   *
   * For instance, a possible extension may filter tests by package
   * name, class name, classes that only implement a certain
   * interface, etc.
   *
   * @param classes the [KClass] for each discovered [Spec]
   *
   * @return  the list of filtered classes to use.
   */
  fun afterScan(classes: List<KClass<out Spec>>): List<KClass<out Spec>> = classes

  /**
   * This function is invoked to create an instance of a [Spec].
   *
   *  By default, test classes are assumed to have zero-arg primary constructors.
   *  If you wish to use non-zero arg primary constructors, this function can
   *  be implemented with logic on how to instantiate a test class.
   *
   * An implementation can choose to create a new instance, or it can
   * choose to return null if it wishes to pass control to the next
   * extension (or if no more extensions, then back to the Test Runner).
   *
   * By overriding this function, extensions are able to customize
   * the way classes are created, to support things like constructors
   * with parameters, or classes that require special initization logic.
   */
  fun <T : Spec> instantiate(clazz: KClass<T>): Spec? = null
}