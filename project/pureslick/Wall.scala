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

class Wall(world: World, pos: Vec2) {
  val image = new Image("data/wall.png")
  val body = createBody(world,pos)

  def createBody(world: World, pos: Vec2) = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.STATIC
    bodyDef.position = pos

    val bodyShape = new PolygonShape()
    bodyShape.setAsBox(image.getWidth / 2, image.getHeight / 2)
    val bodyFixture = new FixtureDef()
    bodyFixture.shape = bodyShape
    bodyFixture.filter.categoryBits = 0x0002
    bodyFixture.filter.maskBits = 0x0004
    bodyFixture.filter.groupIndex = -1

    val footShape = new PolygonShape()
    footShape.setAsBox(image.getWidth / 2, image.getHeight*3/4 / 2, new Vec2(0, image.getHeight/4 / 2), 0f)
    val footFixture = new FixtureDef()
    footFixture.shape = footShape
    footFixture.filter.categoryBits = 0x0001
    footFixture.filter.maskBits = 0x0001
    footFixture.filter.groupIndex = 1

    val body = world.createBody(bodyDef)
    body.createFixture(bodyFixture)
    body.createFixture(footFixture)
    body.setUserData(this)
    body
  }

  def render() = {
    image.draw(body.getPosition.x,body.getPosition.y)
  }
}
