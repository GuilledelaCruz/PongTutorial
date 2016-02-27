pong = {}

width = nil
height = nil

ball = {i = nil, b = nil, s = nil , f = nil}
upperWall = {i = nil, b = nil, s = nil , f = nil}
lowerWall = {i = nil, b = nil, s = nil , f = nil}
leftPanel = {i = nil, b = nil, s = nil , f = nil}
rightPanel = {i = nil, b = nil, s = nil , f = nil}
world = nil

scoreboard = {goalA = 0, goalB = 0}

text = ""

function love.load(arg)

	width = love.graphics.getWidth()
	height = love.graphics.getHeight()

	ball.i = love.graphics.newImage("assets/images/ball.png")
	upperWall.i = love.graphics.newImage("assets/images/wall.png")
	lowerWall.i = love.graphics.newImage("assets/images/wall.png")
	leftPanel.i = love.graphics.newImage("assets/images/panel.png")
	rightPanel.i = love.graphics.newImage("assets/images/panel.png")

	love.physics.setMeter(32)
	world = love.physics.newWorld(0, 0, true)

	ball.b = love.physics.newBody(world, width / 2 - ball.i:getWidth() / 2, height / 2 - ball.i:getHeight() / 2, "dynamic")
	ball.s = love.physics.newCircleShape(ball.i:getWidth() / 2)
	ball.f = love.physics.newFixture(ball.b, ball.s)
	ball.b:setMassData(ball.s:computeMass(1))
	ball.f:setUserData("Ball")

	upperWall.b = love.physics.newBody(world, width / 2, upperWall.i:getHeight() / 2,"static")
	upperWall.s = love.physics.newRectangleShape(upperWall.i:getWidth(), upperWall.i:getHeight())
	upperWall.f = love.physics.newFixture(upperWall.b, upperWall.s)
	upperWall.f:setUserData("Wall")

	lowerWall.b = love.physics.newBody(world, width / 2, height - lowerWall.i:getHeight() / 2,"static")
	lowerWall.s = love.physics.newRectangleShape(lowerWall.i:getWidth(), lowerWall.i:getHeight())
	lowerWall.f = love.physics.newFixture(lowerWall.b, lowerWall.s)
	lowerWall.f:setUserData("Wall")

	leftPanel.b = love.physics.newBody(world, leftPanel.i:getWidth() / 2, height / 2,"static")
	leftPanel.s = love.physics.newRectangleShape(leftPanel.i:getWidth(), leftPanel.i:getHeight())
	leftPanel.f = love.physics.newFixture(leftPanel.b, leftPanel.s)
	leftPanel.f:setUserData("Panel")

	rightPanel.b = love.physics.newBody(world, width - rightPanel.i:getWidth() / 2, height / 2,"static")
	rightPanel.s = love.physics.newRectangleShape(rightPanel.i:getWidth(), rightPanel.i:getHeight())
	rightPanel.f = love.physics.newFixture(rightPanel.b, rightPanel.s)
	rightPanel.f:setUserData("Panel")

	world:setCallbacks(beginContact)

	ball.b:applyLinearImpulse(100, -100)
end

function beginContact(a, b, c)
	local body = b:getBody()
	--text = a:getUserData() .. " " .. b:getUserData()

	if a:getUserData() == "Wall" then
			local y = a:getBody():getY()
			if y < height / 2 then
					body:applyLinearImpulse(0, 200)
			else
					body:applyLinearImpulse(0, -200)
			end
	end
	if a:getUserData() == "Panel" then
		local x = a:getBody():getX()
		if x < width / 2 then
				body:applyLinearImpulse(200, 0)
		else
				body:applyLinearImpulse(-200, 0)
		end
	end
end

function love.update(dt)

	if ball.b:getX() > width then
		scoreboard.goalA = scoreboard.goalA + 1
		ball.b:setPosition(width / 2 - ball.i:getWidth() / 2, height / 2 - ball.i:getHeight() / 2)
	end
	if ball.b:getX() < 0 then
		scoreboard.goalB = scoreboard.goalB + 1
		ball.b:setPosition(width / 2 - ball.i:getWidth() / 2, height / 2 - ball.i:getHeight() / 2)
	end

	world:update(dt)
end

function love.draw(dt)

	love.graphics.draw(ball.i, ball.b:getX() - ball.i:getWidth() / 2, ball.b:getY() - ball.i:getHeight() / 2)
	love.graphics.draw(upperWall.i, upperWall.b:getX() - upperWall.i:getWidth() / 2, upperWall.b:getY() - upperWall.i:getHeight() / 2)
	love.graphics.draw(lowerWall.i, lowerWall.b:getX() - lowerWall.i:getWidth() / 2, lowerWall.b:getY() - lowerWall.i:getHeight() / 2)
	love.graphics.draw(leftPanel.i, leftPanel.b:getX() - leftPanel.i:getWidth() / 2, leftPanel.b:getY() - leftPanel.i:getHeight() / 2)
	love.graphics.draw(rightPanel.i, rightPanel.b:getX() - rightPanel.i:getWidth() / 2, rightPanel.b:getY() - rightPanel.i:getHeight() / 2)

	love.graphics.print(text, 0, 10)
	love.graphics.print(scoreboard.goalA .. "    " .. scoreboard.goalB, width / 2, 10)

end

function love.mousemoved(x, y, dx, dy)
	if y > upperWall.i:getHeight() + rightPanel.i:getHeight() / 2 and
			y < height - lowerWall.i:getHeight() - rightPanel.i:getHeight() / 2 then
		rightPanel.b:setY(y)
	end
end

function love.mousepressed(x, y, dx, dy)
	pong.back()
end

function pong.empty() end

function pong.back()
        -- Clear all callbacks
        love.load = pong.empty
        love.update = pong.empty
        love.draw = pong.empty
        love.keypressed = pong.empty
        love.keyreleased = pong.empty
        love.mousepressed = pong.empty
        love.mousereleased = pong.empty
				love.mousemoved = pong.empty

        love.filesystem.load("main.lua")()

        love.load()
end
