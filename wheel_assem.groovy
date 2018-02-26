makeWheelAssem()
List<Object>  makeWheelAssem(){
	CSG cube = new Cube(10,20,10).toCSG()
	
	return [cube]
}