package application;

import java.awt.print.Printable;
import java.text.DecimalFormat;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jdk.internal.org.objectweb.asm.Handle;


public class RootLayoutController 
{
	//reference field to the main app handle
	private Main mainApp;
	
	//Fields
	//labels
	//Displays the remaining time in the match
	@FXML
	private Label lblTime;
	//Shows Blue's proposed ending points
	@FXML
	private Label lblBlueScore;
	//Shows Red's proposed ending points
	@FXML
	private Label lblRedScore;
	//A dash in between score labels
	@FXML
	private Label lblDash;
	
	
	//text fields
	//Blue's climbing bots text field
	@FXML 
	private TextField txtBlueClimbers;
	//Red's climbing bots text field
	@FXML
	private TextField txtRedClimbers;
	@FXML
	private TextField txtBlueAutoPts;
	@FXML
	private TextField txtRedAutoPts;
	
	//buttons
	//Blue's vault score button 
	@FXML 
	private Button btn1Vault;
	//Red's vault score button
	@FXML
	private Button btn2Vault;
	@FXML 
	private Button btnSwitchAlliance;
	//Switch 1 togglers
	@FXML
	private Button btn1Switch1;
	@FXML
	private Button btn2Switch1;
	@FXML
	private Button btnSwitch1Flip;
	//Switch 2 togglers
	@FXML 
	private Button btn1Switch2;
	@FXML
	private Button btn2Switch2;
	@FXML
	private Button btnSwitch2Flip;
	//Scale togglers
	@FXML
	private Button btn1Scale;
	@FXML
	private Button btn2Scale;
	@FXML
	private Button btnScaleFlip;
	//Start match button
	@FXML 
	private Button btnStart;
	@FXML 
	private Button btnReset;
	@FXML
	private Button btnUpdate;
	
	//Widgets for power ups
	//blue
	//labels
	@FXML
	private Label lblBlueVaultCubes;
	//buttons
	@FXML
	private Button btnBlueForce;
	@FXML
	private Button btnBlueForceUp;
	@FXML
	private Button btnBlueForceDown;
	@FXML
	private Button btnBlueBoost;
	@FXML
	private Button btnBlueBoostUp;
	@FXML
	private Button btnBlueBoostDown;
	@FXML
	private Button btnBlueLevitate;
	//red
	//labels
	@FXML
	private Label lblRedVaultCubes;
	//buttons
	@FXML
	private Button btnRedForce;
	@FXML
	private Button btnRedForceUp;
	@FXML
	private Button btnRedForceDown;
	@FXML
	private Button btnRedBoost;
	@FXML
	private Button btnRedBoostUp;
	@FXML
	private Button btnRedBoostDown;
	@FXML
	private Button btnRedLevitate;
	
	
	String leftSwitch1 = "blue";
	String rightSwitch1 = "red";
	String leftScale = "blue";
	String rightScale = "red";
	String leftSwitch2 = "blue";
	String rightSwitch2 = "red";
	
	String alliance = "blue";
	
	String switch1Owner = "";
	String scaleOwner = "";
	String switch2Owner = "";
	
	int blueActualPts = 0; 
	int blueSwitch1Pts = 0;
	int blueSwitch2Pts = 0;
	int blueScalePts = 0;
	int blueClimbingPts = 0;
	int blueClimbers = 0;
	int blueAutoPts = 0;
	
	int redActualPts = 0;
	int redSwitch1Pts = 0;
	int redSwitch2Pts = 0;
	int redScalePts = 0;
	int redClimbingPts = 0;
	int redClimbers = 0;
	int redAutoPts = 0;
	
	String blue = "#0000ff";
	String red = "#ff0000";
	
	//power up variables
	int blueNumCubes = 0;
	int blueForce = 0;
	int blueForcePts = 0;
	boolean blueForceUsed = false;
	int blueBoost = 0;
	boolean blueBoostUsed = false;
	boolean blueLevitateUsed = false;
	
	int redNumCubes = 0;
	int redForce = 0;
	int redForcePts = 0;
	boolean redForceUsed = false;
	int redBoost = 0;
	boolean redBoostUsed = false;
	boolean redLevitateUsed = false;
	
	boolean switch1Disable = false;
	boolean switch2Disable = false;
	boolean scaleDisable = false;
	
	public RootLayoutController()
	{
		
	}
	
	//stopwatch control
	AnimationTimer stopwatch = new AnimationTimer() 
	{
	
		private static final long STOPPED = -1 ;
		private long startTime = STOPPED ;
		int secondsLeft = 120;
		int tickerTime = 121;
		int blueForceCount = 0;
		int blueBoostCount = 0;
		int blueBoostMultiplier = 1;
		int redForceCount = 0;
		int redBoostCount = 0;
		
		@Override
		public void handle(long timestamp) 
		{
			if (startTime == STOPPED) 
			{
				startTime = timestamp ;
			}
			
			int elapsedseconds = (int) ((timestamp - startTime) /1000000000);
			secondsLeft = 120 - (elapsedseconds);
			
			
			if(secondsLeft == (tickerTime - 1))
			{
				tickerTime--;
				System.out.println(String.valueOf(secondsLeft));
				
				//power ups
				//blue force
				if(blueForceUsed && (blueForceCount < 10))
				{
					//disables red Force while active
					btnRedForce.setDisable(true);
					//disables blue boost while active
					btnBlueBoost.setDisable(true);
					
					blueForceCount++;
					
					if(alliance.equals("blue"))
					{
						if(blueForce == 1)
						{
							switch2Disable = true;
							
							if(switch2Owner.equals("red"))
							{
								blueSwitch2Pts = 0;
								redSwitch2Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 2 (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									switch2Disable = false;
								}
							}
							else if(switch2Owner.equals("blue"))
							{
								redSwitch2Pts = 0;
								blueSwitch2Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 2 (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									switch2Disable = false;
								}
							}
						}
						else if(blueForce == 2)
						{
							scaleDisable = true;
							if(scaleOwner.equals("red"))
							{
								redScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("blue"))
							{
								redScalePts = 0;
								blueScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
						}
						else if (blueForce == 3) 
						{
							switch2Disable = true;
							if(switch2Owner.equals("red"))
							{
								redSwitch2Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 2 (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									switch2Disable = false;
								}
							}
							else if(switch2Owner.equals("blue"))
							{
								redSwitch2Pts = 0;
								blueSwitch2Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 2 (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									switch2Disable = false;
								}
							}
							
							scaleDisable = true;
							if(scaleOwner.equals("red"))
							{
								redScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("blue"))
							{
								redScalePts = 0;
								blueScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
						}
					}
					else if(alliance.equals("red")) 
					{
						if(blueForce == 1)
						{
							switch1Disable = true;
							
							if(switch1Owner.equals("red"))
							{
								blueSwitch1Pts = 0;
								redSwitch1Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 1 (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									switch1Disable = false;
								}
							}
							else if(switch1Owner.equals("blue"))
							{
								redSwitch1Pts = 0;
								blueSwitch1Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 1 (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									switch1Disable = false;
								}
							}
						}
						else if(blueForce == 2)
						{
							scaleDisable = true;
							if(scaleOwner.equals("red"))
							{
								redScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("blue"))
							{
								redScalePts = 0;
								blueScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
						}
						else if (blueForce == 3) 
						{
							switch1Disable = true;
							if(switch1Owner.equals("red"))
							{
								redSwitch1Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 1 (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									switch1Disable = false;
								}
							}
							else if(switch1Owner.equals("blue"))
							{
								redSwitch1Pts = 0;
								blueSwitch1Pts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on switch 1 (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									switch1Disable = false;
								}
							}
							
							scaleDisable = true;
							if(scaleOwner.equals("red"))
							{
								redScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (effective)");
								if(blueForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("blue"))
							{
								redScalePts = 0;
								blueScalePts = secondsLeft - 10 + blueForceCount;
								blueForcePts = 10 - blueForceCount;
								blueActualPts++;
								System.out.println("blue force on scale (ineffective)");
								if(blueForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
						}
					}
					
					if(blueForceCount == 10)
					{
						scaleDisable = false;
						switch1Disable = false;
						switch2Disable = false;
						
						//if red force hasnt been used re enables
						checkRedPowerUps();
						
						//if blue boost hasnt been used re enables
						checkBluePowerUps();
						
						//sets font to regular to show its not in use
						btnBlueForce.setFont(Font.font("System", FontWeight.NORMAL, 12));
					}
				}
				
				//red force
				if(redForceUsed && (redForceCount < 10))
				{
					//disables blue Force while active
					btnBlueForce.setDisable(true);
					//disables blue boost while active
					btnRedBoost.setDisable(true);
					
					redForceCount++;
					System.out.println("red force active");
					
					if(alliance.equals("red"))
					{
						if(redForce == 1)
						{
							switch2Disable = true;
							if(switch2Owner.equals("blue"))
							{
								redSwitch2Pts = 0;
								blueSwitch2Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 2(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									switch2Disable = false;
								}
							}
							else if(switch2Owner.equals("red"))
							{
								blueSwitch2Pts = 0;
								redSwitch2Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 2(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									switch2Disable = false;
								}
							}
						}
						else if(redForce == 2)
						{
							scaleDisable = true;
							if(scaleOwner.equals("blue"))
							{
								blueScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("red"))
							{
								blueScalePts = 0;
								redScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
						}
						else if (redForce == 3) 
						{
							switch2Disable = true;
							if(switch2Owner.equals("blue"))
							{
								redSwitch2Pts = 0;
								blueSwitch2Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 2(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									switch2Disable = false;
								}
							}
							else if(switch2Owner.equals("red"))
							{
								blueSwitch2Pts = 0;
								redSwitch2Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 2(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									switch2Disable = false;
								}
							}
							
							scaleDisable = true;
							if(scaleOwner.equals("blue"))
							{
								blueScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("red"))
							{
								blueScalePts = 0;
								redScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
						}
					}
					else if(alliance.equals("blue")) 
					{
						if(redForce == 1)
						{
							switch1Disable = true;
							if(switch1Owner.equals("blue"))
							{
								redSwitch1Pts = 0;
								blueSwitch1Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 1(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									switch1Disable = false;
								}
							}
							else if(switch1Owner.equals("red"))
							{
								blueSwitch1Pts = 0;
								redSwitch1Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 1(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									switch1Disable = false;
								}
							}
						}
						else if(redForce == 2)
						{
							scaleDisable = true;
							if(scaleOwner.equals("blue"))
							{
								blueScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("red"))
							{
								blueScalePts = 0;
								redScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
						}
						else if (redForce == 3) 
						{
							switch1Disable = true;
							if(switch2Owner.equals("blue"))
							{
								redSwitch1Pts = 0;
								blueSwitch1Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 1(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									switch1Disable = false;
								}
							}
							else if(switch1Owner.equals("red"))
							{
								blueSwitch1Pts = 0;
								redSwitch1Pts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on switch 1(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									switch1Disable = false;
								}
							}
							
							scaleDisable = true;
							if(scaleOwner.equals("blue"))
							{
								blueScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(effective)");
								if(redForceCount == 10)
								{
									blueActualPts--;
									scaleDisable = false;
								}
							}
							else if(scaleOwner.equals("red"))
							{
								blueScalePts = 0;
								redScalePts = secondsLeft - 10 + redForceCount;
								redForcePts = 10 - redForceCount;
								redActualPts++;
								System.out.println("red force on scale(ineffective)");
								if(redForceCount == 10)
								{
									redActualPts--;
									scaleDisable = false;
								}
							}
						}
					}
					if(redForceCount == 10)
					{
						scaleDisable = false;
						switch1Disable = false;
						switch2Disable = false;
						
						//if blue force hasnt been used re enables
						checkBluePowerUps();
						
						//if red boost hasnt been used re enables
						checkRedPowerUps();
						
						//sets font to regular to show its not in use
						btnRedForce.setFont(Font.font("System", FontWeight.NORMAL, 12));
					}
				}
				
				//blue boost 
				if(blueBoostUsed && (blueBoostCount < 10))
				{
					//disables blue force while active
					btnBlueForce.setDisable(true);
					//disables red boost while active
					btnRedBoost.setDisable(true);
					
					blueBoostCount++;
					
					if(alliance.equals("blue"))
					{
						if(blueBoost == 1)
						{
							if(switch2Owner.equals("red"))
							{
								switch2Disable = false;
								System.out.println("blue boost on switch 2 (ineffective)");
							}
							else if(switch2Owner.equals("blue"))
							{
								switch2Disable = true;
								blueActualPts = blueActualPts + 2;
								blueSwitch2Pts = secondsLeft + (10 - blueBoostCount);
								redSwitch2Pts = 0;
								System.out.println("blue boost on switch 2 (effective)");
							}
						}
						else if(blueBoost == 2)
						{
							if(scaleOwner.equals("red"))
							{
								scaleDisable = false;
								System.out.println("blue boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("blue"))
							{
								scaleDisable = true;
								blueActualPts = blueActualPts + 2;
								blueScalePts = secondsLeft + (10 - blueBoostCount);
								redScalePts = 0;
								System.out.println("blue boost on scale (effective)");
							}
						}
						else if (blueBoost == 3) 
						{
							if(switch2Owner.equals("red"))
							{
								switch2Disable = false;
								System.out.println("blue boost on switch 2 (ineffective)");
							}
							else if(switch2Owner.equals("blue"))
							{
								switch2Disable = true;
								blueActualPts = blueActualPts + 2;
								blueSwitch2Pts = secondsLeft + (10 - blueBoostCount);
								redSwitch2Pts = 0;
								System.out.println("blue boost on switch 2 (effective)");
							}
							
							if(scaleOwner.equals("red"))
							{
								scaleDisable = false;
								System.out.println("blue boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("blue"))
							{
								scaleDisable = true;
								blueActualPts = blueActualPts + 2;
								blueScalePts = secondsLeft + (10 - blueBoostCount);
								redScalePts = 0;
								System.out.println("blue boost on scale (effective)");
							}
						}
					}
					else if(alliance.equals("red")) 
					{
						if(blueBoost == 1)
						{
							if(switch1Owner.equals("red"))
							{
								switch1Disable = false;
								System.out.println("blue boost on switch 1 (ineffective)");
							}
							else if(switch1Owner.equals("blue"))
							{
								switch1Disable = true;
								blueActualPts = blueActualPts + 2;
								blueSwitch1Pts = secondsLeft + (10 - blueBoostCount);
								redSwitch1Pts = 0;
								System.out.println("blue boost on switch 1 (effective)");
							}
						}
						else if(blueBoost == 2)
						{
							if(scaleOwner.equals("red"))
							{
								scaleDisable = false;
								System.out.println("blue boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("blue"))
							{
								scaleDisable = true;
								blueActualPts = blueActualPts + 2;
								blueScalePts = secondsLeft + (10 - blueBoostCount);
								redScalePts = 0;
								System.out.println("blue boost on scale (effective)");
							}
						}
						else if (blueBoost == 3) 
						{
							if(switch1Owner.equals("red"))
							{
								switch1Disable = false;
								System.out.println("blue boost on switch 2 (ineffective)");
							}
							else if(switch1Owner.equals("blue"))
							{
								switch1Disable = true;
								blueActualPts = blueActualPts + 2;
								blueSwitch1Pts = secondsLeft + (10 - blueBoostCount);
								redSwitch1Pts = 0;
								System.out.println("blue boost on switch 2 (effective)");
							}
							
							if(scaleOwner.equals("red"))
							{
								scaleDisable = false;
								System.out.println("blue boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("blue"))
							{
								scaleDisable = true;
								blueActualPts = blueActualPts + 2;
								blueScalePts = secondsLeft + (10 - blueBoostCount);
								redScalePts = 0;
								System.out.println("blue boost on scale (effective)");
							}
						}
					}
					
					if(redBoostCount == 10)
					{
						scaleDisable = false;
						switch1Disable = false;
						switch2Disable = false;
						
						//if blue force hasnt been used re enables
						checkBluePowerUps();
						
						//if red boost hasnt been used re enables
						checkRedPowerUps();
						
						//sets font to regular to show its not in use
						btnBlueBoost.setFont(Font.font("System", FontWeight.NORMAL, 12));
					}
				}
				
				//red boost 
				if(redBoostUsed && (redBoostCount < 10))
				{
					//disables blue force while active
					btnRedForce.setDisable(true);
					//disables red boost while active
					btnRedBoost.setDisable(true);
					
					redBoostCount++;
					
					if(alliance.equals("red"))
					{
						if(redBoost == 1)
						{
							if(switch2Owner.equals("blue"))
							{
								switch2Disable = false;
								System.out.println("red boost on switch 2 (ineffective)");
							}
							else if(switch2Owner.equals("red"))
							{
								switch2Disable = true;
								redActualPts = redActualPts + 2;
								redSwitch2Pts = secondsLeft + (10 - redBoostCount);
								blueSwitch2Pts = 0;
								System.out.println("red boost on switch 2 (effective)");
							}
						}
						else if(redBoost == 2)
						{
							if(scaleOwner.equals("blue"))
							{
								scaleDisable = false;
								System.out.println("red boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("red"))
							{
								scaleDisable = true;
								redActualPts = redActualPts + 2;
								redScalePts = secondsLeft + (10 - redBoostCount);
								blueScalePts = 0;
								System.out.println("red boost on scale (effective)");
							}
						}
						else if (redBoost == 3) 
						{
							if(switch2Owner.equals("blue"))
							{
								switch2Disable = false;
								System.out.println("red boost on switch 2 (ineffective)");
							}
							else if(switch2Owner.equals("red"))
							{
								switch2Disable = true;
								redActualPts = redActualPts + 2;
								redSwitch2Pts = secondsLeft + (10 - redBoostCount);
								redSwitch2Pts = 0;
								System.out.println("red boost on switch 2 (effective)");
							}
							
							if(scaleOwner.equals("blue"))
							{
								scaleDisable = false;
								System.out.println("red boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("red"))
							{
								scaleDisable = true;
								redActualPts = redActualPts + 2;
								redScalePts = secondsLeft + (10 - redBoostCount);
								blueScalePts = 0;
								System.out.println("red boost on scale (effective)");
							}
						}
					}
					else if(alliance.equals("blue")) 
					{
						if(redBoost == 1)
						{
							if(switch1Owner.equals("blue"))
							{
								switch1Disable = false;
								System.out.println("red boost on switch 1 (ineffective)");
							}
							else if(switch1Owner.equals("red"))
							{
								switch1Disable = true;
								redActualPts = redActualPts + 2;
								redSwitch1Pts = secondsLeft + (10 - redBoostCount);
								blueSwitch1Pts = 0;
								System.out.println("red boost on switch 1 (effective)");
							}
						}
						else if(redBoost == 2)
						{
							if(scaleOwner.equals("blue"))
							{
								scaleDisable = false;
								System.out.println("red boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("red"))
							{
								scaleDisable = true;
								redActualPts = redActualPts + 2;
								redScalePts = secondsLeft + (10 - redBoostCount);
								blueScalePts = 0;
								System.out.println("red boost on scale (effective)");
							}
						}
						else if (redBoost == 3) 
						{
							if(switch1Owner.equals("blue"))
							{
								switch1Disable = false;
								System.out.println("red boost on switch 2 (ineffective)");
							}
							else if(switch1Owner.equals("red"))
							{
								switch1Disable = true;
								redActualPts = redActualPts + 2;
								redSwitch1Pts = secondsLeft + (10 - redBoostCount);
								blueSwitch1Pts = 0;
								System.out.println("red boost on switch 2 (effective)");
							}
							
							if(scaleOwner.equals("blue"))
							{
								scaleDisable = false;
								System.out.println("red boost on scale (ineffective)");
							}
							else if(scaleOwner.equals("red"))
							{
								scaleDisable = true;
								redActualPts = redActualPts + 2;
								redScalePts = secondsLeft + (10 - redBoostCount);
								blueScalePts = 0;
								System.out.println("red boost on scale (effective)");
							}
						}
					}
					
					if(redBoostCount == 10)
					{
						scaleDisable = false;
						switch1Disable = false;
						switch2Disable = false;
						
						//if red force hasnt been used re enables
						checkRedPowerUps();
						
						//if blue boost hasnt been used re enables
						checkBluePowerUps();
					}
				}
				
				//setting actual points for switch 1
				if((switch1Owner.equals("red")) && !switch1Disable)
				{
					redActualPts++;
					redSwitch1Pts = secondsLeft;
					blueSwitch1Pts = 0;
				}
				else if((switch1Owner.equals("blue")) && !switch1Disable)
				{
					blueActualPts++;
					blueSwitch1Pts = secondsLeft;
					redSwitch1Pts = 0;
				}
				
				//setting actual points for switch 2 (((1 <= blueForceCount) && (blueForceCount < 10)) || (redForceCount != 0))
				if((switch2Owner.equals("red")) && !switch2Disable)
				{
					redActualPts++;
					redSwitch2Pts = secondsLeft;
					blueSwitch2Pts = 0;
					System.out.println("red Switch 2");
				}
				else if((switch2Owner.equals("blue")) && !switch2Disable)
				{
					blueActualPts++;
					blueSwitch2Pts = secondsLeft;
					redSwitch2Pts = 0;
					System.out.println("blue Switch 2");
				}
				
				//setting actual points for scale
				if(scaleOwner.equals("red") && !scaleDisable)
				{
					redActualPts++;
					redScalePts = secondsLeft;
					blueScalePts = 0;
				}
				else if(scaleOwner.equals("blue") && !scaleDisable)
				{
					blueActualPts++;
					blueScalePts = secondsLeft;
					redScalePts = 0;
				}
			}
			
			
			lblTime.setText(String.valueOf(secondsLeft));
			updateScore();
			
			if(secondsLeft == 0)
			{
				stopwatch.stop();
			}        
		}
	
		@Override
		public void stop() 
		{
			startTime = STOPPED ;
			super.stop();
			startTime = -1;
		}
	};
	
	@FXML
	private void initialize()
	{
		//labels
		lblTime.setText("120");
		lblBlueScore.setText("0");
		lblBlueScore.setTextFill(Color.web("#0000ff"));
		lblRedScore.setText("0");
		lblRedScore.setTextFill(Color.web("#ff0000"));
		lblDash.setText("-");
		
		//text fields
		txtBlueClimbers.setPromptText("Climbing bots");
		txtRedClimbers.setPromptText("Climbing bots");
		
		//buttons
		btn1Switch1.setText("Switch");
		btn1Switch1.setTextFill(Color.web("#0000ff"));
		btn1Switch1.setDisable(true);
		btn2Switch1.setText("Switch");
		btn2Switch1.setTextFill(Color.web("#ff0000"));
		btn2Switch1.setDisable(true);
		btn1Switch2.setText("Switch");
		btn1Switch2.setTextFill(Color.web("#0000ff"));
		btn1Switch2.setDisable(true);
		btn2Switch2.setText("Switch");
		btn2Switch2.setTextFill(Color.web("#ff0000"));
		btn2Switch2.setDisable(true);
		btn1Scale.setText("Scale");
		btn1Scale.setTextFill(Color.web("#0000ff"));
		btn1Scale.setDisable(true);
		btn2Scale.setText("Scale");
		btn2Scale.setTextFill(Color.web("#ff0000"));
		btn2Scale.setDisable(true);
		btn1Vault.setText("Vault");
		btn1Vault.setTextFill(Color.web("#0000ff"));
		btn1Vault.setDisable(true);
		btn2Vault.setText("Vault");
		btn2Vault.setTextFill(Color.web("#ff0000"));
		btn2Vault.setDisable(true);
		btnStart.setText("Start");
		btnUpdate.setText("Update");
		btnReset.setText("Reset");
		btnReset.setDisable(true);
		
		disableGameButtons(true);
		disablePowerUps(true);
	}
	
	@FXML
	public void handleStart()
	{
		stopwatch.start();
		disableGameButtons(false);
		btnReset.setDisable(false);
		btnStart.setDisable(true);
		btnSwitchAlliance.setDisable(true);
	}
	
	@FXML
	public void handleReset()
	{
		//TODO reset button
		stopwatch.stop();
		disableGameButtons(true);
		disablePowerUps(true);
		btnReset.setDisable(true);
		btnStart.setDisable(false);
	}
	
	@FXML
	public void handleUpdate() 
	{
		txtBlueAutoPts.setEditable(false);
		txtBlueClimbers.setEditable(false);
		txtRedAutoPts.setEditable(false);
		txtRedClimbers.setEditable(false);
		btnUpdate.setDisable(true);
		readTextFields();
		updateScore();

	}
	
	@FXML
	public void handleSwitch1Flip() 
	{
		if(leftSwitch1.equals("blue"))
		{
			btn1Switch1.setTextFill(Color.web(red));
			leftSwitch1 = "red";
			btn2Switch1.setTextFill(Color.web(blue));
			rightSwitch1 = "blue";
		}
		else
		{
			btn1Switch1.setTextFill(Color.web(blue));
			leftSwitch1 = "blue";
			btn2Switch1.setTextFill(Color.web(red));
			rightSwitch1 = "red";
		}
		
		lblBlueScore.setText(String.valueOf(blueActualPts + blueSwitch1Pts + blueSwitch2Pts + blueScalePts));
		lblRedScore.setText(String.valueOf(redActualPts + redSwitch1Pts + redSwitch2Pts + redScalePts));
	}
	
	@FXML
	public void handleSwitch2Flip()
	{
		if(leftSwitch2.equals("blue"))
		{
			btn1Switch2.setTextFill(Color.web(red));
			leftSwitch2 = "red";
			btn2Switch2.setTextFill(Color.web(blue));
			rightSwitch2 = "blue";
		}
		else
		{
			btn1Switch2.setTextFill(Color.web(blue));
			leftSwitch2 = "blue";
			btn2Switch2.setTextFill(Color.web(red));
			rightSwitch2 = "red";
		}
	}
	
	@FXML
	public void handleScaleFlip()
	{
		if(leftScale.equals("blue"))
		{
			btn1Scale.setTextFill(Color.web(red));
			leftScale = "red";
			btn2Scale.setTextFill(Color.web(blue));
			rightScale = "blue";
		}
		else
		{
			btn1Scale.setTextFill(Color.web(blue));
			leftScale = "blue";
			btn2Scale.setTextFill(Color.web(red));
			rightScale = "red";
		}
	}
	
	@FXML
	public void handleSwitchAlliance()
	{
		if(alliance == "blue")
		{
			btn1Vault.setTextFill(Color.web(red));
			btn2Vault.setTextFill(Color.web(blue));
			alliance = "red";
		}
		else
		{
			btn1Vault.setTextFill(Color.web(blue));
			btn2Vault.setTextFill(Color.web(red));
			alliance = "blue";
		}
	}
	
	@FXML
	public void handle1Switch1()
	{
		if(leftSwitch1.equals("blue"))
		{
			//is blues button
			switch1Owner = "blue";
		}
		else 
		{
			//is reds button
			switch1Owner = "red";
		}
		btn1Switch1.setDisable(true);
		btn2Switch1.setDisable(false);
		
		btnSwitch1Flip.setDisable(true);
	}
	
	@FXML
	public void handle2Switch1()
	{
		if(rightSwitch1.equals("blue"))
		{
			//is blues button
			switch1Owner = "blue";
		}
		else 
		{
			//is reds button
			switch1Owner = "red";
		}
		btn2Switch1.setDisable(true);
		btn1Switch1.setDisable(false);
		
		btnSwitch1Flip.setDisable(true);
	}
	
	@FXML
	public void handle1Switch2()
	{
		if(leftSwitch2.equals("blue"))
		{
			//is Blues button
			switch2Owner = "blue";
		}
		else 
		{
			//is Reds button
			switch2Owner = "red";
		}
		btn2Switch2.setDisable(false);
		btn1Switch2.setDisable(true);
		
		btnSwitch2Flip.setDisable(true);
	}
	
	@FXML
	public void handle2Switch2()
	{
		if(rightSwitch2.equals("blue"))
		{
			//is Blues button
			switch2Owner = "blue";
		}
		else 
		{
			//is Reds button
			switch2Owner = "red";
		}
		btn2Switch2.setDisable(true);
		btn1Switch2.setDisable(false);
		
		btnSwitch2Flip.setDisable(true);
	}
	
	@FXML
	public void handle1Scale()
	{
		if(leftScale.equals("blue"))
		{
			//is Blues button
			scaleOwner = "blue";
		}
		else 
		{
			//is Reds button
			scaleOwner = "red";
		}
		btn1Scale.setDisable(true);
		btn2Scale.setDisable(false);
		
		btnScaleFlip.setDisable(true);
	}
	
	@FXML
	public void handle2Scale()
	{
		if(rightScale.equals("blue"))
		{
			//is Blues button
			scaleOwner = "blue";
		}
		else 
		{
			//is Reds button
			scaleOwner = "red";
		}
		btn1Scale.setDisable(false);
		btn2Scale.setDisable(true);
		
		btnScaleFlip.setDisable(true);
	}

	@FXML
	public void handleVault1()
	{
		if(alliance.equals("blue"))
		{
			//is blues button
			blueActualPts = blueActualPts + 5;
			blueNumCubes++;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			checkBluePowerUps();
		}
		else
		{
			//is reds button
			redActualPts = redActualPts + 5;
			redNumCubes++;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
		}
	}
	
	@FXML
	public void handleVault2()
	{
		if(alliance.equals("blue"))
		{
			//is reds button
			redActualPts = redActualPts + 5;
			redNumCubes++;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
		}
		else
		{
			//is blues button
			blueActualPts = blueActualPts + 5;
			blueNumCubes++;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			checkBluePowerUps();
		}
	}
	
	//power ups
	//blue
	//force
	@FXML
	public void handleBlueForce()
	{
		if((1 <= blueForce) && (blueForce <= 3))
		{
			//blue's switch owned for 10 seconds
			blueForceUsed = true;
			btnBlueForce.setDisable(true);
			btnBlueForceUp.setDisable(true);
			btnBlueForceDown.setDisable(true);
			
			//sets font to bold to show its in use
			btnBlueForce.setFont(Font.font("System", FontWeight.BOLD, 12));
		}
	}
	
	@FXML
	public void handleBlueForceUp()
	{
		//increment force tier up
		if((blueForce <= 3) && (blueNumCubes != 0))
		{
			blueForce++;
			btnBlueForce.setText("Force " + String.valueOf(blueForce));
			
			blueNumCubes--;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			
			if((blueForce == 3) || (blueNumCubes == 0))
			{
				btnBlueForceUp.setDisable(true);
			}
			checkBluePowerUps();
			
			if((blueForce >= 1) && (blueForce <= 3))
			{
				btnBlueForce.setDisable(false);
			}
		}
	}
	
	@FXML
	public void handleBlueForceDown()
	{
		//increment force tier down
		if(blueForce > 0)
		{
			blueForce--;
			btnBlueForce.setText("Force " + String.valueOf(blueForce));
			
			blueNumCubes++;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			checkBluePowerUps();
			
			if((blueForce >= 1) && (blueForce <= 3))
			{
				btnBlueForce.setDisable(false);
			}
			else if(blueForce == 0)
			{
				btnBlueForceDown.setDisable(true);
				btnBlueForce.setDisable(true);
				btnBlueForce.setText("Force");
			}
		}
	}
	
	//boost
	@FXML
	public void handleBlueBoost()
	{
		if((1 <= blueBoost) && (blueBoost <= 3))
		{
			//blue's switch owned for 10 seconds
			blueBoostUsed = true;
			btnBlueBoost.setDisable(true);
			btnBlueBoostUp.setDisable(true);
			btnBlueBoostDown.setDisable(true);
			
			//sets font to bold to show its in use
			btnBlueBoost.setFont(Font.font("System", FontWeight.BOLD, 12));
		}
	}
	
	@FXML
	public void handleBlueBoostUp()
	{
		//increment boost tier
		if((blueBoost <= 3) && (blueNumCubes != 0))
		{
			blueBoost++;
			btnBlueBoost.setText("Boost " + String.valueOf(blueBoost));
			
			blueNumCubes--;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			checkBluePowerUps();
			
			if((blueBoost == 3) || (blueNumCubes == 0))
			{
				btnBlueBoostUp.setDisable(true);
			}
			
			if((blueBoost >= 1) && (blueBoost <= 3))
			{
				btnBlueBoost.setDisable(false);
			}
		}
	}
	
	@FXML
	public void handleBlueBoostDown()
	{
		//decrement boost tier
		if(blueBoost > 0)
		{
			blueBoost--;
			btnBlueBoost.setText("Boost " + String.valueOf(blueBoost));
			
			blueNumCubes++;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			checkBluePowerUps();
			
			if((blueBoost >= 1) && (blueBoost <= 3))
			{
				btnBlueBoost.setDisable(false);
			}
			else if(blueBoost == 0)
			{
				btnBlueBoostDown.setDisable(true);
				btnBlueBoost.setDisable(true);
				btnBlueBoost.setText("Boost");
			}
		}
	}
	
	//levitate
	@FXML
	public void handleBlueLevitate()
	{
		if((blueClimbers < 3) && (blueNumCubes >= 3))
		{
			blueNumCubes = blueNumCubes - 3;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			blueClimbingPts = blueClimbingPts + 25;
			btnBlueLevitate.setDisable(true);
			blueLevitateUsed = true;
			
			
		}
	}
	
	//red
	//force
	@FXML
	public void handleRedForce()
	{
		if((1 <= redForce) && (redForce <= 3))
		{
			//blue's switch owned for 10 seconds
			redForceUsed = true;
			btnRedForce.setDisable(true);
			btnRedForceUp.setDisable(true);
			btnRedForceDown.setDisable(true);
			
			//sets font to bold to show its in use
			btnRedForce.setFont(Font.font("System", FontWeight.BOLD, 12));
		}
	}
	
	@FXML
	public void handleRedForceUp()
	{
		//increment force tier up
		if((redForce <= 3) && (redNumCubes != 0))
		{
			redForce++;
			btnRedForce.setText("Force " + String.valueOf(redForce));
			
			redNumCubes--;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
			
			if((redForce == 3) || (redNumCubes == 0))
			{
				btnRedForceUp.setDisable(true);
			}
			
			if((redForce >= 1) && (redForce <= 3))
			{
				btnRedForce.setDisable(false);
			}
		}
	}
	
	@FXML
	public void handleRedForceDown()
	{
		//increment force tier down
		if(redForce > 0)
		{
			redForce--;
			btnRedForce.setText("Force " + String.valueOf(redForce));
			
			redNumCubes++;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
			
			if((redForce >= 1) && (redForce <= 3))
			{
				btnRedForce.setDisable(false);
			}
			else if(redForce == 0)
			{
				btnRedForceDown.setDisable(true);
				btnRedForce.setDisable(true);
				btnRedForce.setText("Force");
			}
		}
	}
	
	//boost
	@FXML
	public void handleRedBoost()
	{
		if((1 <= redBoost) && (redBoost <= 3))
		{
			//blue's switch owned for 10 seconds
			redBoostUsed = true;
			btnRedBoost.setDisable(true);
			btnRedBoostUp.setDisable(true);
			btnRedBoostDown.setDisable(true);
			
			//sets font to bold to show its in use
			btnRedBoost.setFont(Font.font("System", FontWeight.BOLD, 12));
		}
	}
	
	@FXML
	public void handleRedBoostUp()
	{
		//increment boost tier
		if((redBoost <= 3) && (redNumCubes != 0))
		{
			redBoost++;
			btnRedBoost.setText("Boost " + String.valueOf(redBoost));
			
			redNumCubes--;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
			
			if((redBoost == 3) || (redNumCubes == 0))
			{
				btnRedBoostUp.setDisable(true);
			}
			
			if((redBoost >= 1) && (redBoost <= 3))
			{
				btnRedBoost.setDisable(false);
			}
		}
	}
	
	@FXML
	public void handleRedBoostDown()
	{
		//decrement boost tier
		if(redBoost > 0)
		{
			redBoost--;
			btnRedBoost.setText("Boost " + String.valueOf(redBoost));
			
			redNumCubes++;
			lblRedVaultCubes.setText(String.valueOf(redNumCubes));
			
			checkRedPowerUps();
			
			if((redBoost >= 1) && (redBoost <= 3))
			{
				btnRedBoost.setDisable(false);
			}
			else if(redBoost == 0)
			{
				btnRedBoostDown.setDisable(true);
				btnRedBoost.setDisable(true);
				btnRedBoost.setText("Boost");
			}
		}
	}
	
	//levitate
	@FXML
	public void handleRedLevitate()
	{
		if((redClimbers < 3) && (redNumCubes >= 3))
		{
			redNumCubes = redNumCubes - 3;
			lblBlueVaultCubes.setText(String.valueOf(blueNumCubes));
			
			redClimbingPts = redClimbingPts + 25;
			btnRedLevitate.setDisable(true);
		}
	}
	
	public void updateScore()
	{
		lblBlueScore.setText(String.valueOf(blueActualPts + blueSwitch1Pts + blueSwitch2Pts + blueScalePts + 
				blueClimbingPts + blueAutoPts + blueForcePts));
		lblRedScore.setText(String.valueOf(redActualPts + redSwitch1Pts + redSwitch2Pts + redScalePts + 
				redClimbingPts + redAutoPts + redForcePts));
	}
	
	public void readTextFields()
	{
		blueAutoPts = Integer.parseInt(txtBlueAutoPts.getText());
		blueClimbers = Integer.parseInt(txtBlueClimbers.getText());
		blueClimbingPts = (blueClimbers * 30) + ((3 - blueClimbers) * 5);
		
		redAutoPts = Integer.parseInt(txtRedAutoPts.getText());
		redClimbers = Integer.parseInt(txtRedClimbers.getText());
		redClimbingPts = (redClimbers * 30) + ((3 - redClimbers) * 5);
	}
	
	public void disableGameButtons(boolean x)
	{
		btn1Vault.setDisable(x);
		btn2Vault.setDisable(x);
		btn1Scale.setDisable(x);
		btn2Scale.setDisable(x);
		btn1Switch1.setDisable(x);
		btn2Switch1.setDisable(x);
		btn1Switch2.setDisable(x);
		btn2Switch2.setDisable(x);
	}
	
	public void disablePowerUps(boolean x)
	{
		btnBlueForce.setDisable(x);
		btnBlueForceUp.setDisable(x);
		btnBlueForceDown.setDisable(x);
		btnBlueBoost.setDisable(x);
		btnBlueBoostUp.setDisable(x);
		btnBlueBoostDown.setDisable(x);
		btnBlueLevitate.setDisable(x);
		
		btnRedForce.setDisable(x);
		btnRedForceUp.setDisable(x);
		btnRedForceDown.setDisable(x);
		btnRedBoost.setDisable(x);
		btnRedBoostUp.setDisable(x);
		btnRedBoostDown.setDisable(x);
		btnRedLevitate.setDisable(x);
		
	}
	
	public void checkBluePowerUps()
	{
		if((blueNumCubes >= 3) && !blueLevitateUsed)
		{
			btnBlueLevitate.setDisable(false);
		}
		
		if(!blueForceUsed) 
		{
			if((blueForce != 3) && (blueNumCubes != 0))
			{
				btnBlueForceUp.setDisable(false);
			}
			else 
			{
				btnBlueForceUp.setDisable(true);
			}
			
			if(blueForce != 0)
			{
				btnBlueForceDown.setDisable(false);
				btnBlueForce.setDisable(false);
			}
			else
			{
				btnBlueForceDown.setDisable(true);
				btnBlueForce.setDisable(true);
			}
		}
		else 
		{
			btnBlueForce.setDisable(true);
			btnBlueForceUp.setDisable(true);
			btnBlueForceDown.setDisable(true);
		}
			
		if(!blueBoostUsed)
		{
			if((blueBoost != 3) && (blueNumCubes != 0))
			{
				btnBlueBoostUp.setDisable(false);
			}
			else 
			{
				btnBlueBoostUp.setDisable(true);
			}
			
			if(blueBoost != 0)
			{
				btnBlueBoostDown.setDisable(false);
				btnBlueBoost.setDisable(false);
			}
			else
			{
				btnBlueBoostDown.setDisable(true);
				btnBlueBoost.setDisable(true);
			}
		}
		else
		{
			btnBlueBoost.setDisable(true);
			btnBlueBoostUp.setDisable(true);
			btnBlueBoostDown.setDisable(true);
		}
		
		
	}
	
	public void checkRedPowerUps()
	{
		if(!redLevitateUsed)
		{
			if(redNumCubes >= 3)
			{
				btnRedLevitate.setDisable(false);
			}
		}
		
		if(!redForceUsed)
		{
			if((redForce != 3) && (redNumCubes != 0))
			{
				btnRedForceUp.setDisable(false);
			}
			else 
			{
				btnRedForceUp.setDisable(true);
			}
			
			if(redForce != 0)
			{
				btnRedForceDown.setDisable(false);
				btnRedForce.setDisable(false);
			}
			else
			{
				btnRedForceDown.setDisable(true);
				btnRedForce.setDisable(true);
			}
		}
		else 
		{
			btnRedForce.setDisable(true);
			btnRedForceUp.setDisable(true);
			btnRedForceDown.setDisable(true);
		}
			
		if(!redBoostUsed)
		{
			if((redBoost != 3) && (redNumCubes != 0))
			{
				btnRedBoostUp.setDisable(false);
			}
			else 
			{
				btnRedBoostUp.setDisable(true);
			}
			
			if(redBoost != 0)
			{
				btnRedBoostDown.setDisable(false);
				btnRedBoost.setDisable(false);
			}
			else
			{
				btnRedBoostDown.setDisable(true);
				btnRedBoost.setDisable(true);
			}
		}
		
	}
	
	public void setMainApp(Main mainApp) 
	{
		this.mainApp = mainApp;
	}
}


































