package pureslick

import org.jbox2d.dynamics.{FixtureDef, BodyType, BodyDef, World}
import org.jbox2d.common.Vec2
import org.jbox2d.collision.shapes.PolygonShape
import org.newdawn.slick.Image

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/25/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */

class Wall(world: World, pos: Vec2) extends GameObject {
  var image = new Image("data/wall.png")
  val body = createBody(world, pos)
  var x = body.getPosition.x
  var y = body.getPosition.y

  def this(world: World, x: Float, y: Float) = {
    this(world,new Vec2(x,y))
  }
  def createBody(world: World, pos: Vec2) = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.STATIC
    bodyDef.position = pos

    val footShape = new PolygonShape()
    footShape.setAsBox(image.getWidth / 20, image.getHeight * 3 / 4 / 20, new Vec2(0, image.getHeight / 4 / 20), 0f)
    val footFixture = new FixtureDef()
    footFixture.shape = footShape
    footFixture.filter.categoryBits = 0x0001
    footFixture.filter.maskBits = 0x0007
    footFixture.filter.groupIndex = 1

    val body = world.createBody(bodyDef)
    body.createFixture(footFixture)
    body.setUserData(this)
    body
  }
}
