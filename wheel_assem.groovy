// makeWheelAssem()
makeWheelAssem()
// wheel(60, 25)
// axleNub(6.25, 8, 15)
//drivenGear(42, 20, 10, 5)

List<Object>  makeWheelAssem(){
    int thickness = 12
	CSG top = plate(200, thickness)
	CSG servo = Vitamins.get("hobbyServo","hv6214mg").rotx(180).rotz(-90)
	servo = servo.movez(thickness*2+37).movex(52.5)
    CSG bottom = plate(200, thickness).rotx(180)
    bottom = bottom.movez(thickness*2+37).difference(servo)
    CSG wheel = wheel(60, 25, 2).rotx(180).movez(thickness+35)
    
    CSG mGear = drivenGear(28, 20, 10, 5)
    mGear = mGear.movez(thickness+26).movex(52.5)
	
	return [top, bottom, wheel, mGear]
}

CSG plate(int length, int thickness) {
    int dist = length/2
    int width = 80
    CSG plate = new Cube(dist*2,width,thickness).toCSG().movez(thickness/2)
    CSG cylinder = new Cylinder(width/2,thickness,30).toCSG().movex(-dist).union(plate)
    CSG bearing = new Cylinder(14, 6, 30).toCSG()
    CSG hole = new Cylinder(8.5, thickness, 30).toCSG().union(bearing).movex(-dist)
    cylinder = cylinder.difference(hole).rotx(180).movez(thickness).movex(dist)

    return cylinder
}

CSG wheel(int radius, int wheelThickness, int washerThickness) {
    int gearThickness = 9
    CSG axleHole = new Cylinder(2.25, wheelThickness, 30).toCSG()
    CSG axle = axleNub(6.25, 8, 15, washerThickness).movez(wheelThickness)
    CSG wheel = new Cylinder(radius, wheelThickness, 30).toCSG().union(axle).difference(axleHole).movez(gearThickness)
    CSG wGear = gear(45, 30, gearThickness, 5).union(axle.rotx(180).movez(wheelThickness))
    wheel = wheel.union(wGear)
    return wheel.setColor(javafx.scene.paint.Color.BLUE)
}

CSG axleNub(double radius, double width, double length, int washerThickness) {
    CSG washer = new Cylinder(8.5, washerThickness, 30).toCSG()
    CSG box = new Cube(radius*2, width, length).toCSG().movez(length/2)
    CSG axleCyl = new Cylinder(radius, length, 30).toCSG().intersect(box).movez(washerThickness)
    axleCyl = axleCyl.union(washer)

    return axleCyl
}

CSG drivenGear(int teeth, int radius, int thickness, int toothDepth) {
	CSG washer = new Cylinder(radius, 1, 30).toCSG().movez(10)
	CSG mGear = gear(teeth, radius, thickness, toothDepth).union(washer)
	CSG horn = Vitamins.get("hobbyServoHorn","hv6214mg_6").rotx(180)
	horn = horn.movez(5)
	for(int i=0; i<5; i++) {
		mGear = mGear.difference(horn.movez(3*i))
	}
	return mGear
}

CSG gear(int teeth, int radius, int thickness, int toothDepth) {
    CSG round = new Cylinder(radius, radius, thickness, (int)30).toCSG()
	CSG tooth = new Cube(toothDepth, toothDepth, thickness).toCSG().movez(thickness/2)
	tooth = tooth.difference(new Cube(7, 8, thickness)
				 .toCSG()
				 .rotz(-20)
				 .movex(-5)
				 .movez(thickness/2))
				 .difference(new Cube(7, 8, thickness)
					     .toCSG()
					     .rotz(20)
					     .movex(5)
					     .movez(thickness/2))
				.movey(-radius)
	round = round.union(tooth)

	// CSG axleHole = new Cube(3.175, 3.175, height).toCSG().movez(height/2)
	// //CSG axleCylinder = new Cylinder(4, 4, height, (int)30).toCSG()
	// round = round.difference(axleHole)
	
	for (int i = 0; i < teeth; i++) {
		round = round.union(tooth.rotz(360/teeth*i))
	}
    return round
}