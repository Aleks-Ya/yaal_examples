package samples

import org.scalatest.funspec.AnyFunSpec

import scala.collection.mutable

class ExampleSpec extends AnyFunSpec {

  describe("An ArrayStack") {

    it("should pop values in last-in-first-out order") {
      val stack = new mutable.Stack[Int]
      stack.push(1)
      stack.push(2)
      assert(stack.pop() === 2)
      assert(stack.pop() === 1)
    }

    it("should throw RuntimeException if an empty array stack is popped") {
      val emptyStack = new mutable.Stack[Int]
      intercept[RuntimeException] {
        emptyStack.pop()
      }
    }
  }
}
