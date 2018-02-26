makeWheelAssem()

List<Object>  makeWheelAssem(){
	CSG cube = topGuard()
	
	return [cube]
}

CSG topGuard() {
    int dist = 50
    int thickness = 12
    int width = 80
    CSG plate = new Cube(dist*2,width,thickness).toCSG().movez(thickness/2)
    CSG cylinder = new Cylinder(width/2,thickness,30).toCSG().movex(-dist).union(plate)
    CSG bearing = new Cylinder(28, 6, 30).toCSG()
    CSG hole = new Cylinder(13, thickness, 30).toCSG().union(bearing).movex(-dist)
    cylinder = cylinder.difference(hole).rotx(180).movez(thickness)

    return cylinder
}

CSG wheel(int radius, int axleHoleRadius) {
    int wheelThickness = 10
    CSG axleHole = new Cylinder(axleHoleRadius, wheelThickness, 30).toCSG()
    CSG wheel = new Cylinder(radius, wheelThickness, 30).toCSG().difference(axleHole)

    return wheel
}