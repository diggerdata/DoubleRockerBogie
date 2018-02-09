import com.neuronrobotics.bowlerstudio.creature.ICadGenerator;
import com.neuronrobotics.bowlerstudio.creature.CreatureLab;
import org.apache.commons.io.IOUtils;
import com.neuronrobotics.bowlerstudio.vitamins.*;
import java.nio.file.Paths;
import eu.mihosoft.vrl.v3d.FileUtil;
import com.neuronrobotics.bowlerstudio.vitamins.*;
import javafx.scene.transform.*;
println "Loading STL file"
// Load an STL file from a git repo
// Loading a local file also works here

return new ICadGenerator(){
	@Override 
	public ArrayList<CSG> generateCad(DHParameterKinematics d, int linkIndex) {
		ArrayList<DHLink> dhLinks = d.getChain().getLinks()
		ArrayList<CSG> allCad=new ArrayList<CSG>()
	
		DHLink dh = dhLinks.get(linkIndex)
		// Hardware to engineering units configuration
		LinkConfiguration conf = d.getLinkConfiguration(linkIndex);
		// Engineering units to kinematics link (limits and hardware type abstraction)
		AbstractLink abstractLink = d.getAbstractLink(linkIndex);// Transform used by the UI to render the location of the object
		// Transform used by the UI to render the location of the object
		Affine manipulator = dh.getListener();


		if (linkIndex==0){
			// File wheel_file = ScriptingEngine.fileFromGit(
			// "https://github.com/NeuronRobotics/NASACurisoity.git",
			// "STL/wheel.STL");
			// File tire_file = ScriptingEngine.fileFromGit(
			// "https://github.com/NeuronRobotics/NASACurisoity.git",
			// "STL/tire.STL");
			/*
			CSG wheel = Vitamins.get(wheel_file)
			wheel=wheel			
					.movex(-wheel.getMaxX()/2)
					.movey(-wheel.getMaxY()/2)
					.movez(-wheel.getMaxZ()/2)
					.rotx(90)
			wheel.setManipulator(manipulator)
			
			allCad.add(wheel)
			*/
			double gearThickness = 3
			
			List<Object> bevelGears = (List<Object>)ScriptingEngine
				.gitScriptRun(
				"https://github.com/madhephaestus/GearGenerator.git", // git location of the library
				"bevelGear.groovy" , // file to load
				// Parameters passed to the funcetion
				[	18,// Number of teeth gear a
					18,// Number of teeth gear b
					gearThickness,// thickness of gear A
					2,// gear pitch in arch length mm
					90
				]
            )
			CSG tire = new Cylinder(dh.getR(),dh.getR(),10,(int)30)
					.toCSG()
					.rotx(90)
					.movex(-dh.getR())
					.movez(-dh.getD())
			CSG axleHole = new Cube(3.175, 3.175, dh.getR()*2)
				.toCSG()
				.rotx(-90)
				.movex(-dh.getR())
				.movez(-dh.getD())

			CSG axleHole2 = new Cube(3.175, 3.175, dh.getR()*2)
				.toCSG()
				// .rotx(-90)
				.movex(-dh.getR())
				.movez(-dh.getD())
			
			gears= [bevelGears.get(0),bevelGears.get(1)]
			tire = tire.difference(axleHole).movey(-bevelGears.get(2)*2)

			gears[0] = gears[0].rotx(180).movez(-dh.getD()+bevelGears.get(2)).movex(-dh.getR()).difference(axleHole2)
			gears[1] = gears[1].rotz(-90).movez(dh.getD()-bevelGears.get(2)).movex(-dh.getR()).difference(axleHole)

			gears[0].setManipulator(manipulator)
			gears[1].setManipulator(manipulator)
			tire.setManipulator(manipulator)
			

			allCad.add(gears[0])
			allCad.add(gears[1])
			allCad.add(tire)
		}
		for(int i=0;i<allCad.size();i++){
			allCad.get(i).setColor(javafx.scene.paint.Color.GRAY)
		}
		return allCad;
	}
	@Override 
	public ArrayList<CSG> generateBody(MobileBase b ) {return new ArrayList<>();}
};
