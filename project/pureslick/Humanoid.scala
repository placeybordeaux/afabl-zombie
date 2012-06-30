package pureslick

import org.jbox2d.dynamics._
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.newdawn.slick.Image

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/21/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */

trait Humanoid extends GameObject {
  val image: Image
  val body: Body
  var health: Int

  def die = {
    isGarbage = true
  }

  def damaged(amount: Int) = {
    health -= amount
    if (health < 0) die
  }

  def createBody(world: World): Body = {
    createBody(world, new Vec2(0, 0))
  }

  def createBody(world: World, pos: Vec2) = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.DYNAMIC
    bodyDef.position = pos

    val bodyShape = new PolygonShape()
    bodyShape.setAsBox(40 / 2, 60 / 2)
    val bodyFixture = new FixtureDef()
    bodyFixture.shape = bodyShape
    bodyFixture.filter.categoryBits = 0x0002
    bodyFixture.filter.maskBits = 0x0004
    bodyFixture.filter.groupIndex = -1

    val footShape = new PolygonShape()
    footShape.setAsBox(40 / 2, 20 / 2, new Vec2(0, 40 / 2), 0f)
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
    image.draw(body.getPosition.x - image.getWidth/2, body.getPosition.y - image.getHeight/2)
  }


}
