package pureslick

import org.jbox2d.dynamics.{FixtureDef, BodyType, BodyDef, World}
import org.jbox2d.common.Vec2
import org.jbox2d.collision.shapes.PolygonShape
import org.newdawn.slick.Image

/**
 * Created with IntelliJ IDEA.
 * User: peter
 * Date: 6/22/12
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */

class Bullet(world: World, pos: Vec2, velc: Vec2) extends Renderable {

  val body = createBody(world, pos, velc)
  val image = new Image("data/bullet.png")
  var collided = false
  var x = pos.x
  var y = pos.y

  def collide() = {
    collided = true
    body.destroyFixture(body.getFixtureList)
    body.getWorld().destroyBody(body)
  }

  def createBody(world: World, pos: Vec2, velc: Vec2) = {
    val bodyDef = new BodyDef()
    bodyDef.`type` = BodyType.DYNAMIC
    bodyDef.position = pos
    bodyDef.bullet = true

    val bodyShape = new PolygonShape()
    bodyShape.setAsBox(10 / 2, 10 / 2)
    val bodyFixture = new FixtureDef()
    bodyFixture.shape = bodyShape
    bodyFixture.density = 0.00005f
    bodyFixture.filter.categoryBits = 0x0004
    bodyFixture.filter.maskBits = 0x0007
    bodyFixture.filter.groupIndex = -2

    val body = world.createBody(bodyDef)
    body.createFixture(bodyFixture)
    body.setLinearVelocity(velc)
    body.setUserData(this)
    body
  }

  def render() = {
    image.draw(body.getPosition.x, body.getPosition.y)
  }

  def update = {
    x = body.getPosition.x
    y = body.getPosition.y
  }


}
