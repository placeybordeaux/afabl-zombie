package pureslick

import org.jbox2d.callbacks.RayCastCallback
import org.jbox2d.dynamics.Fixture
import org.jbox2d.common.Vec2

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 7/17/12
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */

class tracingCallback extends RayCastCallback {
  var gameObject: GameObject = null
  def reportFixture(p1: Fixture, p2: Vec2, p3: Vec2, p4: Float) = {
    gameObject = p1.getUserData.asInstanceOf[GameObject]
    1f
  }
}
