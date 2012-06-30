package pureslick

import org.jbox2d.dynamics.Body

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/30/12
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */

trait GameObject extends Renderable {
  var isGarbage = false
  val body: Body

  def update() = {}

  def update(observation: Observation): Unit = {
    update()
  }

}
