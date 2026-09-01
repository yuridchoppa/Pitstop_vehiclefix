package com.example.vehiclefix.data.repository

import com.example.vehiclefix.data.models.*

object VehicleIssueRepository {

    private val issues: List<VehicleIssue> = listOf(
        // ================= 3-WHEELER / AUTO RICKSHAW ISSUES =================
        VehicleIssue(
            id = "auto_clutch_cable",
            title = "Snapped or Loose Clutch Cable",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Transmission & Controls",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "15-20 mins",
            summary = "The clutch lever feels limp and the auto-rickshaw / tuk-tuk will not shift into 1st, 2nd, 3rd, or 4th gear without stalling or grinding.",
            symptoms = listOf(
                "Clutch lever has zero resistance",
                "Gears grinding when shifting",
                "Engine stalls immediately when shifting into 1st gear",
                "Snapping noise heard near the handlebar or engine casing"
            ),
            commonCauses = listOf(
                "Frayed clutch inner wire inside outer sheath",
                "Clutch wire barrel nipple slipped or broken",
                "Lack of lubrication causing excessive wire friction"
            ),
            safetyPrecautions = listOf(
                "Park on level ground and apply the parking handbrake",
                "Switch off the ignition and let the engine cool down",
                "Block the rear wheels with a wooden chock or stone"
            ),
            toolsNeeded = listOf(
                ToolItem("t1", "10mm & 12mm Open/Ring Spanners", true),
                ToolItem("t2", "Pliers / Wire Cutter", true),
                ToolItem("t3", "Flathead Screwdriver", true),
                ToolItem("t4", "Grease or Engine Oil for Lube", false, "WD-40 Spray")
            ),
            partsNeeded = listOf(
                PartItem("p1", "New 3-Wheeler Clutch Inner Wire with Nipple", "₹80 - ₹150 ($1 - $2)"),
                PartItem("p2", "Clutch Wire Barrel Nipple / Clamp Bolt", "₹30 ($0.40)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Inspect Lever and Barrel Nipple",
                    "Check the clutch lever on the left handlebar. Loosen the cable adjuster nut and line up the slots to release the broken nipple.",
                    "If the outer black sheath is undamaged, you only need to replace the inner steel wire.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Disconnect Cable at the Engine End",
                    "Locate the clutch release arm at the rear/side engine bay. Loosen the 10mm pinch bolt holding the inner wire and pull the old broken wire completely out.",
                    "Keep the small spring and cable guide washer safe.",
                    "Beware of hot exhaust pipes near the clutch housing.",
                    0
                ),
                FixStep(
                    3,
                    "Lubricate and Feed the New Inner Wire",
                    "Apply a small coat of grease or motor oil to the new inner wire. Feed it into the outer sheath starting from the handlebar lever down towards the engine.",
                    "Twist slightly while pushing to ensure it glides through without snagging.",
                    null,
                    0
                ),
                FixStep(
                    4,
                    "Fasten Nipple and Set Free Play",
                    "Seat the top nipple into the clutch lever. At the engine arm, pull the wire tight with pliers and tighten the 10mm pinch bolt. Adjust the barrel adjuster to leave 2-3mm of lever free play.",
                    "Ensure the rear wheel spins freely with clutch pulled in before driving.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Clutch snap click, gear grinding sound",
            visualSymptom = "Limp clutch lever, broken wire strand at handle",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "auto_carb_overflow",
            title = "Carburetor Overflow & Spark Plug Flooding",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Fuel & Intake",
            severity = Severity.HIGH,
            difficulty = Difficulty.MODERATE_DIY,
            estimatedTime = "25-35 mins",
            summary = "Fuel dripping continuously from the carburetor overflow pipe, strong petrol smell, and engine failing to start (starting trouble).",
            symptoms = listOf(
                "Petrol leaking from bottom carb drain pipe",
                "Strong petrol smell around passenger cabin",
                "Engine cranks/kicks but will not fire",
                "Spark plug tip is wet with raw fuel"
            ),
            commonCauses = listOf(
                "Stuck float pin / needle valve due to dirt in fuel",
                "Defective float puncture filling with fuel",
                "Fuel tank rust particles bypassing the fuel strainer"
            ),
            safetyPrecautions = listOf(
                "STRICTLY NO SMOKING OR OPEN FLAMES nearby",
                "Turn OFF the fuel petcock (fuel tap) immediately",
                "Work in an open ventilated area"
            ),
            toolsNeeded = listOf(
                ToolItem("t5", "Spark Plug Spanner / 16mm deep socket", true),
                ToolItem("t6", "Phillips / Crosshead Screwdriver", true),
                ToolItem("t7", "Clean Cotton Rag / Cloth", true),
                ToolItem("t8", "Wire Brush or Sandpaper for Spark Plug", true)
            ),
            partsNeeded = listOf(
                PartItem("p3", "Carburetor Float Pin & Needle Valve", "₹120 - ₹250 ($2 - $3)"),
                PartItem("p4", "Fuel Filter In-Line", "₹50 - ₹100 ($1)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Shut Off Fuel Tap and Tap Float Bowl",
                    "Turn the fuel tap to 'OFF'. Gently tap the side of the carburetor float chamber with a plastic screwdriver handle to dislodge a stuck needle valve.",
                    "Often small debris causes the needle to stick; light tapping unseats the grit.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Remove and Dry the Spark Plug",
                    "Unplug the spark plug cap. Use the spark plug spanner to unscrew the plug. If the electrode is soaked with petrol, dry it using a clean rag and lightly scrub with sandpaper.",
                    "Check the electrode gap (approx 0.7mm - 0.8mm).",
                    "Do not over-torque when putting the plug back in aluminum cylinder heads.",
                    0
                ),
                FixStep(
                    3,
                    "Clear Excess Fuel from Combustion Chamber",
                    "With spark plug removed and fuel tap OFF, kick-start or crank the engine 5-6 times to blow out unburnt fuel from the cylinder.",
                    null,
                    "Keep face away from the spark plug hole.",
                    0
                ),
                FixStep(
                    4,
                    "Reinstall and Test Start",
                    "Thread the dried spark plug by hand first, tighten snugly with spanner, reattach spark cap, turn fuel tap to 'ON', and start without choke.",
                    "If overflow persists, the float bowl needs to be removed and cleaned.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Engine cranking hollowly without firing, backfiring puff",
            visualSymptom = "Petrol dripping puddle beneath carburetor",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "e_rickshaw_throttle_hall",
            title = "E-Rickshaw 48V/60V Throttle & Hall Sensor Failure",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Electrical & EV Powertrain",
            severity = Severity.HIGH,
            difficulty = Difficulty.DIFFICULT_PRO_RECOMMENDED,
            estimatedTime = "30-50 mins",
            summary = "E-Rickshaw power turns on and lights work, but turning the throttle accelerator produces no motor response or causes jerking / motor vibration.",
            symptoms = listOf(
                "Digital dash turns on (48V/60V indicated), but vehicle does not move",
                "Motor produces a buzzing / humming sound when accelerator is twisted",
                "Intermittent jerky acceleration when handlebar is turned",
                "Brake cutoff switch stuck in activated mode"
            ),
            commonCauses = listOf(
                "Damaged Hall sensor in brushless DC (BLDC) motor",
                "Faulty magnetic hall throttle accelerator assembly",
                "Brake lever sensor stuck, constantly cutting motor power",
                "Loose 3-pin or 5-pin wire coupler connector under chassis"
            ),
            safetyPrecautions = listOf(
                "Turn OFF the main MCB / circuit breaker under the driver seat before touching wires",
                "Avoid short-circuiting high-current 48V/60V battery terminals",
                "Inspect with insulated multimeter probes"
            ),
            toolsNeeded = listOf(
                ToolItem("t9", "Digital Multimeter (DC Voltage mode)", true),
                ToolItem("t10", "Electrical Insulation Tape & Zip Ties", true),
                ToolItem("t11", "Wire Stripper / Needle Nose Pliers", true)
            ),
            partsNeeded = listOf(
                PartItem("p5", "E-Rickshaw 3-Pin Throttle Handle Assembly", "₹250 - ₹450 ($3 - $5.50)"),
                PartItem("p6", "BLDC Motor Hall Sensor Board (41F)", "₹200 - ₹350 ($2.50 - $4)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Check Brake Cutoff Switches",
                    "Inspect the front and rear hand/foot brake levers. If the microswitch is stuck pressed, the controller disables the motor.",
                    "Disconnect the 2-pin brake cutoff wires to test if throttle starts working.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Inspect Main Wiring Harness Couplers",
                    "Check the wiring harness under the seat near the motor controller. Ensure the 3-pin throttle connector (Red +5V, Black Ground, Green Signal) is seated firmly and not oxidized.",
                    "Spray contact cleaner or clean pins if rust is present.",
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Measure Throttle Output Voltage with Multimeter",
                    "Switch ignition ON. Place multimeter black probe on Black wire and red probe on Green wire. Voltage should smoothly rise from ~0.8V (idle) to ~4.2V (full twist).",
                    "If output stays 0V with 5V input present, replace throttle assembly.",
                    "Keep drive wheels jacked up during throttle test.",
                    0
                ),
                FixStep(
                    4,
                    "Diagnose Motor Hall Sensor",
                    "If throttle output is normal but motor shudders and growls, one of the 3 internal motor hall sensors (A, B, C) is blown. This requires opening motor or visit to EV specialist.",
                    "Recommended to contact nearest EV Garage if motor hall board needs soldering.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Motor buzzing / humming without rotation",
            visualSymptom = "Battery meter shows full, but no throttle response",
            isStrandedHazard = true
        ),

        // ================= CAR / VEHICLE GENERAL ISSUES =================
        VehicleIssue(
            id = "car_flat_tire",
            title = "Flat Tire / Puncture & Spare Wheel Replacement",
            vehicleType = VehicleType.CAR,
            category = "Tires & Wheels",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "15-25 mins",
            summary = "Tire has lost air pressure due to nail puncture or sidewall rupture. Requires jacking up the car and mounting the spare wheel / donut.",
            symptoms = listOf(
                "Vehicle pulls strongly to one side while driving",
                "Thumping or flapping noise from tire",
                "TPMS (Tire Pressure Monitoring System) warning light active",
                "Tire sidewall resting on the road rim"
            ),
            commonCauses = listOf(
                "Nail, screw, or sharp debris puncture in tread",
                "Damaged tire valve stem or bead leak",
                "Pothole impact causing rim bend or sidewall pinch"
            ),
            safetyPrecautions = listOf(
                "Park on SOLID, FLAT ground away from traffic flow",
                "Turn ON Hazard warning lights and place Warning Triangle 50m behind car",
                "Engage Handbrake firmly and put car in 1st Gear (Manual) or 'P' Park (Auto)"
            ),
            toolsNeeded = listOf(
                ToolItem("t12", "Car Scissor / Hydraulic Jack", true),
                ToolItem("t13", "Lug Wrench (Wheel Spanner)", true),
                ToolItem("t14", "Wheel Chocks (or large rocks/bricks)", true),
                ToolItem("t15", "Tire Pressure Gauge / Portable Inflator", false)
            ),
            partsNeeded = listOf(
                PartItem("p7", "Spare Wheel / Space Saver (Inside Trunk)", "Included in Car"),
                PartItem("p8", "Tubeless Tire Puncture Repair Plug Kit", "₹150 - ₹300 ($2 - $4)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Secure Vehicle and Loosen Lug Nuts",
                    "Chock the diagonally opposite wheel. Before lifting the car, use the lug wrench to loosen all wheel nuts 1/2 turn counter-clockwise (do not remove yet).",
                    "Always loosen while tire has traction with the ground.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Position Jack at Designated Lift Point",
                    "Locate the reinforced notch / jack point on the car frame sill just behind the front wheel or ahead of the rear wheel. Turn the jack screw clockwise until the wheel clears the road by 2 inches.",
                    null,
                    "NEVER place any part of your body under a car supported solely by a jack.",
                    0
                ),
                FixStep(
                    3,
                    "Swap Wheel and Hand-Tighten Nuts",
                    "Remove lug nuts completely, pull off flat tire, and mount the spare wheel onto the hub bolts. Thread nuts by hand in a cross (star) pattern until snug.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    4,
                    "Lower Car and Torque Nuts Fully",
                    "Lower the jack until the spare tire touches the ground. Use lug wrench to firmly torque all nuts in a star pattern. Put flat tire in trunk.",
                    "Check spare tire pressure at nearest fuel station. Space-saver spares max speed is 80 km/h (50 mph).",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Rhythmic flapping or heavy road rumbling",
            visualSymptom = "Tire visibly deflated and sitting flat on rim",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "car_dead_battery",
            title = "Dead Battery Jump-Start & Terminal Corrosion",
            vehicleType = VehicleType.CAR,
            category = "Battery & Electrical",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "10-15 mins",
            summary = "Engine produces a rapid clicking sound or complete silence when trying to turn the ignition key, dashboard lights flicker or stay dim.",
            symptoms = listOf(
                "Rapid clicking noise ('tik-tik-tik') on turning key",
                "Dashboard lights dim heavily or reset when cranking",
                "Headlights are faint and power windows operate very slowly",
                "White/blue powdery corrosion on battery terminals"
            ),
            commonCauses = listOf(
                "Headlights or cabin lights left ON overnight",
                "Battery aged beyond 3-4 years losing chemical capacity",
                "Alternator not charging 12V battery during drive",
                "Heavy sulfate corrosion on terminal clamps"
            ),
            safetyPrecautions = listOf(
                "Never connect Red (+) to Black (-) clamp (Will blow main fuse)",
                "Keep sparks and open flames away from lead-acid battery",
                "Wear protective gloves and safety glasses"
            ),
            toolsNeeded = listOf(
                ToolItem("t16", "Jumper Cables (Red & Black Clamps)", true),
                ToolItem("t17", "10mm Spanner / Terminal Wrench", true),
                ToolItem("t18", "Wire Brush or Sandpaper", true),
                ToolItem("t19", "Hot Water & Baking Soda Solution", false)
            ),
            partsNeeded = listOf(
                PartItem("p9", "Replacement 12V Car Battery (35Ah - 65Ah)", "₹3,500 - ₹6,500 ($45 - $80)"),
                PartItem("p10", "Petroleum Jelly / Terminal Anti-Corrosion Spray", "₹100 ($1.20)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Clean Battery Terminal Corrosion",
                    "Pour warm water or baking soda solution over terminals to dissolve acidic white crust. Use wire brush to expose clean bare lead metal for solid conductivity.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Connect Jumper Cables in Correct Sequence",
                    "Connect RED clamp to Dead Battery (+), RED clamp to Helper Car (+), BLACK clamp to Helper Car (-), and finally BLACK clamp to unpainted engine metal bracket of Dead Car.",
                    "Do NOT connect final black clamp to dead battery negative terminal to prevent spark ignition.",
                    "Double-check polarities before starting.",
                    0
                ),
                FixStep(
                    3,
                    "Start Helper Car and Rev Engine",
                    "Start helper car's engine and let it run at 2,000 RPM for 3 minutes to transfer surface charge to the dead battery.",
                    null,
                    null,
                    180
                ),
                FixStep(
                    4,
                    "Start Dead Car and Disconnect in Reverse Order",
                    "Crank the dead car. Once it starts, let both run for 2 minutes, then disconnect cables in EXACT REVERSE order: (Dead engine ground, Helper -, Helper +, Dead +).",
                    "Drive car continuously for at least 25-30 minutes to allow alternator to recharge the battery.",
                    null,
                    0
                )
            ),
            obdCode = "P0562",
            audioSymptom = "Rapid clicking relay sound, no engine crank",
            visualSymptom = "Battery warning light on dash, dim headlights",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "car_engine_overheating",
            title = "Engine Overheating & Coolant Leak / Boiling",
            vehicleType = VehicleType.CAR,
            category = "Cooling System",
            severity = Severity.CRITICAL_STRANDED,
            difficulty = Difficulty.DIFFICULT_PRO_RECOMMENDED,
            estimatedTime = "30-45 mins",
            summary = "Temperature gauge pinned in the RED 'H' zone, steam/smoke issuing from under hood, and sweet coolant smell. Engine is in danger of seizing or blowing head gasket.",
            symptoms = listOf(
                "Temperature needle in RED 'H' zone",
                "Steam or hissing sound from radiator/expansion tank",
                "Heater blowing cold air despite high engine temp",
                "Puddle of green, red, or pink sweet-smelling fluid beneath car"
            ),
            commonCauses = listOf(
                "Low coolant level from radiator hose puncture or cracked tank",
                "Radiator cooling fan motor failed / blown fan fuse",
                "Stuck thermostat valve remaining closed",
                "Water pump impeller failure or snapped serpentine belt"
            ),
            safetyPrecautions = listOf(
                "PULL OVER AND SHUT OFF ENGINE IMMEDIATELY",
                "NEVER OPEN RADIATOR CAP WHILE HOT - Boiling coolant will erupt causing 3rd-degree burns",
                "Wait minimum 15-20 minutes for engine to cool before checking levels"
            ),
            toolsNeeded = listOf(
                ToolItem("t20", "Thick Cloth / Rag", true),
                ToolItem("t21", "Flashlight / Torch", true),
                ToolItem("t22", "Pliers / Screwdriver for Hose Clamp", false)
            ),
            partsNeeded = listOf(
                PartItem("p11", "Prediluted 50/50 Engine Coolant (1-2 Litres)", "₹250 - ₹500 ($3 - $6)"),
                PartItem("p12", "Distilled / Clean Drinking Water (Emergency Refill)", "₹20 ($0.25)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Safely Stop and Wait for Engine Cooldown",
                    "Pull to roadside, switch OFF AC, turn ON heater to maximum to dissipate core heat, and turn off engine. Pop hood release latch from inside.",
                    "Do NOT pour cold water directly over a hot engine block as it can crack the cylinder head.",
                    "Do NOT open radiator cap yet.",
                    900
                ),
                FixStep(
                    2,
                    "Inspect for Hose Bursts and Fan Operation",
                    "Check the upper and lower radiator hoses for splits or loose clamps. Check the translucent plastic expansion reservoir coolant level.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Carefully Open Reservoir and Top Up Fluid",
                    "Once radiator is cool to the touch, cover cap with thick cloth and slowly twist half a turn to relieve pressure, then open. Top up with coolant or clean distilled water.",
                    null,
                    "Ensure cap is tightly closed after filling.",
                    0
                ),
                FixStep(
                    4,
                    "Triage: Test Drive or Call Nearest Garage",
                    "Start engine and check if temperature stabilizes in the middle. If fan does not spin or coolant immediately boils out, do NOT drive further. Request tow or nearest mechanic.",
                    "Driving with blown head gasket can cause catastrophic engine destruction.",
                    null,
                    0
                )
            ),
            obdCode = "P0217",
            audioSymptom = "Hissing steam sound, bubbling coolant boiling",
            visualSymptom = "Steam from under hood, temperature gauge in RED",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "obd_p0300_misfire",
            title = "OBD-II P0300: Random / Multiple Cylinder Misfire",
            vehicleType = VehicleType.CAR,
            category = "Engine & Ignition",
            severity = Severity.HIGH,
            difficulty = Difficulty.DIFFICULT_PRO_RECOMMENDED,
            estimatedTime = "40-60 mins",
            summary = "Check Engine Light flashing or solid on dashboard. Car shakes, jerks, vibrates violently during idle and acceleration, with severe power loss.",
            symptoms = listOf(
                "Flashing or solid Yellow 'Check Engine' (MIL) indicator",
                "Engine shakes heavily at idle and stumbles on throttle",
                "Unburnt fuel odor from exhaust",
                "Substantial loss of power and poor fuel economy"
            ),
            commonCauses = listOf(
                "Fouled, worn, or oil-soaked spark plugs",
                "Faulty ignition coil pack (COP)",
                "Clogged or leaking fuel injector",
                "Vacuum leak in intake manifold gasket"
            ),
            safetyPrecautions = listOf(
                "If Check Engine light is FLASHING, do NOT drive at highway speeds (unburnt fuel will melt catalytic converter)",
                "Allow engine to cool down before handling ignition coils and spark plugs"
            ),
            toolsNeeded = listOf(
                ToolItem("t23", "OBD-II Bluetooth Scanner / Code Reader", true),
                ToolItem("t24", "Spark Plug Socket (14mm/16mm) & Ratchet Extension", true),
                ToolItem("t25", "10mm Socket / Wrench for Ignition Coil Bolts", true),
                ToolItem("t26", "Feeler Gauge (Spark Plug Gap Tool)", false)
            ),
            partsNeeded = listOf(
                PartItem("p13", "Iridium / Platinum Spark Plugs (Set of 4)", "₹1,200 - ₹2,800 ($15 - $35)"),
                PartItem("p14", "Ignition Coil Pack (OEM)", "₹1,500 - ₹3,500 ($18 - $42)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Read OBD-II Codes to Identify Cylinders",
                    "Plug OBD-II scanner into the 16-pin port beneath steering column. Note specific codes: P0301 (Cylinder 1), P0302 (Cylinder 2), P0303 (Cylinder 3), P0304 (Cylinder 4).",
                    "Specific cylinder codes pinpoint exactly which coil or spark plug to inspect.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Swap Ignition Coil to Test",
                    "Unbolt the suspected coil and swap it with an adjacent healthy cylinder. Clear OBD codes and restart engine. If misfire code shifts to the new cylinder, that ignition coil is defective.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Inspect and Replace Spark Plugs",
                    "Remove spark plugs and check electrodes. Black carbon indicates rich mixture; white blistered electrode indicates overheating; wet fuel indicates no spark.",
                    "Set plug gap to factory spec (typically 0.8mm - 1.1mm).",
                    "Do not cross-thread spark plugs into cylinder head.",
                    0
                ),
                FixStep(
                    4,
                    "Clear Fault Codes and Road Test",
                    "Use OBD scanner to erase P0300 code, take a gentle 10-minute test drive, and verify smooth acceleration without flashing engine light.",
                    null,
                    null,
                    0
                )
            ),
            obdCode = "P0300",
            audioSymptom = "Stuttering exhaust note, rough engine shudder",
            visualSymptom = "Flashing Check Engine Light (MIL) on dashboard",
            isStrandedHazard = false
        ),

        VehicleIssue(
            id = "car_brake_spongy",
            title = "Spongy Brake Pedal & Brake Fluid Air Bleeding",
            vehicleType = VehicleType.CAR,
            category = "Brakes & Safety",
            severity = Severity.CRITICAL_STRANDED,
            difficulty = Difficulty.EXPERT_GARAGE_ONLY,
            estimatedTime = "45-60 mins",
            summary = "Brake pedal travels all the way to the floorboard before biting, or requires pumping to stop the car. Extreme stopping distance hazard.",
            symptoms = listOf(
                "Brake pedal feels soft, spongy, and sinks to the floor",
                "Need to pump brakes multiple times to slow down",
                "Brake warning icon active on dash",
                "Oily brake fluid residue near caliper or wheel drum"
            ),
            commonCauses = listOf(
                "Air bubbles trapped inside hydraulic brake lines",
                "Low brake fluid level in master cylinder reservoir",
                "Leaking brake caliper piston seal or rusted hard line",
                "Failing brake master cylinder internal bypass"
            ),
            safetyPrecautions = listOf(
                "DO NOT DRIVE ON PUBLIC ROADS with compromised brakes",
                "Brake fluid (DOT 3 / DOT 4) eats car paint - wipe spills immediately with water",
                "Always use fresh, sealed brake fluid bottle"
            ),
            toolsNeeded = listOf(
                ToolItem("t27", "8mm & 10mm Flare Nut Spanner", true),
                ToolItem("t28", "Clear Plastic Bleeder Hose & Catch Bottle", true),
                ToolItem("t29", "Car Jack and Jack Stands", true)
            ),
            partsNeeded = listOf(
                PartItem("p15", "DOT 4 High Performance Brake Fluid (500ml)", "₹200 - ₹450 ($2.50 - $5.50)"),
                PartItem("p16", "Brake Bleeder Valve Screws (Set)", "₹150 ($1.80)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Check Master Cylinder Fluid Level",
                    "Open hood and check translucent brake fluid reservoir. If empty, fluid has leaked out and air entered the ABS/hydraulic circuits.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Locate Bleeder Screws on Calipers",
                    "Jack up vehicle safely and remove wheels. Start bleeding from the wheel furthest from master cylinder (Rear Right -> Rear Left -> Front Right -> Front Left).",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Perform 2-Person Bleed or One-Way Valve Bleed",
                    "Attach clear tubing to bleeder nipple into bottle half-filled with brake fluid. Have helper pump pedal 3 times and HOLD DOWN. Open bleeder screw 1/4 turn to expel air bubbles, then close before helper releases pedal.",
                    "Never let master cylinder reservoir run dry during bleeding.",
                    null,
                    0
                ),
                FixStep(
                    4,
                    "Verify Firm Pedal Pressure & Pro Garage Triage",
                    "Repeat until only solid fluid without bubbles emerges. Top up reservoir to MAX line. If pedal remains mushy, ABS pump or master cylinder requires garage pressure bleed equipment.",
                    "If unsure, use Nearest Garage Finder for professional brake servicing.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Hissing air sound from footwell when depressing brake",
            visualSymptom = "Brake warning light, low fluid in reservoir",
            isStrandedHazard = true
        ),
        VehicleIssue(
            id = "auto_brake_shoe_pulling",
            title = "3-Wheeler Brake Drum Shoe Wear & Pulling to One Side",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Brakes & Steering",
            severity = Severity.HIGH,
            difficulty = Difficulty.MODERATE_DIY,
            estimatedTime = "30-40 mins",
            summary = "Auto-rickshaw violently pulls to the left or right when foot brake is applied, or stopping power is dangerously weak on downslopes.",
            symptoms = listOf(
                "Auto swerves sharply to left or right when pressing foot brake pedal",
                "High squealing noise from rear wheels under braking",
                "Excessive brake pedal travel before shoes bite",
                "One rear brake drum feels scalding hot after driving"
            ),
            commonCauses = listOf(
                "Uneven wear on left vs right rear brake shoes",
                "Rear wheel cylinder oil seal leak contaminating brake friction lining",
                "Broken shoe return spring inside drum assembly",
                "Unequal star-wheel brake adjuster tension"
            ),
            safetyPrecautions = listOf(
                "Secure front wheel with chocks before lifting rear axle",
                "Use sturdy jack stands under rear swingarm / axle",
                "Do NOT breathe in brake dust - spray brake cleaner"
            ),
            toolsNeeded = listOf(
                ToolItem("t30", "19mm / 22mm Rear Hub Nut Socket", true),
                ToolItem("t31", "Brake Spring Plier / Screwdriver", true),
                ToolItem("t32", "Wire Brush & Sandpaper (80 grit)", true),
                ToolItem("t33", "Brake Cleaner Aerosol Spray", false)
            ),
            partsNeeded = listOf(
                PartItem("p17", "3-Wheeler Rear Brake Shoes (Pair)", "₹220 - ₹380 ($3 - $5)"),
                PartItem("p18", "Brake Shoe Tension Spring Kit", "₹60 ($0.75)"),
                PartItem("p19", "Rear Wheel Cylinder Oil Seal", "₹40 ($0.50)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Remove Rear Wheel and Brake Drum",
                    "Jack up rear axle, remove wheel lug nuts, remove split pin from center axle castle nut, and slide off brake drum.",
                    "If drum is stuck, turn brake adjuster star wheel through backing plate slot to retract shoes.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Inspect Shoes and Wheel Cylinder for Leaks",
                    "Check for oily brake fluid on linings. If lining is wet with fluid, the wheel cylinder seal has failed and shoes must be replaced.",
                    "Never reuse oil-contaminated brake shoes.",
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Replace Shoes and Clean Backing Plate",
                    "Unhook top and bottom return springs, fit new brake shoes onto anchor pin, apply thin grease to shoe contact pads on backing plate, and reattach springs.",
                    null,
                    "Keep grease strictly off friction surface.",
                    0
                ),
                FixStep(
                    4,
                    "Equalize Star-Wheel Adjusters on Both Sides",
                    "Rotate star adjuster until drum lightly drags when spun by hand, then back off 2 clicks. Ensure both left and right wheels rotate with identical drag.",
                    "Road test on an empty road to verify straight-line braking without pulling.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "High-pitched brake squeal, metal friction scrape",
            visualSymptom = "Auto swerving sideways under braking",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "e_rickshaw_battery_imbalance",
            title = "E-Rickshaw 48V Battery Pack Voltage Imbalance",
            vehicleType = VehicleType.THREE_WHEELER,
            category = "Battery & EV Powertrain",
            severity = Severity.HIGH,
            difficulty = Difficulty.MODERATE_DIY,
            estimatedTime = "30-45 mins",
            summary = "E-Rickshaw range drops suddenly from 80 km to under 30 km, battery indicator drops 2-3 bars on full throttle, and charger turns green prematurely.",
            symptoms = listOf(
                "Severe range drop after standard full charge",
                "Battery meter on handlebar drops suddenly under load",
                "One 12V battery in the 4-pack series gets unusually hot during charge",
                "Sulfuric acid smell or bubbling in lead-acid cells"
            ),
            commonCauses = listOf(
                "One weak/dead cell in 48V series pack (4x 12V batteries in series)",
                "Loose, corroded, or undersized inter-battery connecting cables",
                "Unequal specific gravity due to low distilled water in cells"
            ),
            safetyPrecautions = listOf(
                "Wear rubber gloves and safety eye goggles",
                "Turn off main MCB breaker before touching battery terminals",
                "Work in a well-ventilated area (hydrogen gas risk)"
            ),
            toolsNeeded = listOf(
                ToolItem("t34", "Digital Multimeter (DC Voltage)", true),
                ToolItem("t35", "Battery Hydrometer (Specific Gravity Tester)", true),
                ToolItem("t36", "10mm & 13mm Insulated Spanners", true)
            ),
            partsNeeded = listOf(
                PartItem("p20", "Distilled Water for Battery Cells (5 Litres)", "₹50 - ₹100 ($1)"),
                PartItem("p21", "Heavy Gauge 25sqmm Battery Inter-Connect Cables", "₹120 ($1.50)"),
                PartItem("p22", "12V 100Ah-140Ah E-Rickshaw Battery (If cell dead)", "₹6,500 - ₹9,500 ($80 - $115)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Measure Individual 12V Battery Voltages",
                    "Switch off ignition and let pack rest 30 mins. Use multimeter on each of the 4 batteries. Healthy batteries should read 12.6V - 12.8V. A battery reading < 11.8V is weak/imbalanced.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Check Electrolyte Levels and Top Up",
                    "Unscrew vent caps on all cells. If lead plates are exposed to air, top up with pure distilled water up to the MAX indicator collar.",
                    null,
                    "NEVER add battery acid (H2SO4) - only use distilled water.",
                    0
                ),
                FixStep(
                    3,
                    "Clean and Tighten Inter-Connecting Cables",
                    "Remove corroded copper interconnect terminals, brush clean with wire brush, and re-torque firmly. Apply petroleum jelly on terminal posts.",
                    "Loose connection causes high resistance and thermal melting.",
                    null,
                    0
                ),
                FixStep(
                    4,
                    "Equalize Individual Battery Charge or Replace",
                    "Charge the single weak 12V battery individually with a 12V smart charger to bring it up to match the others, or replace the single defective battery.",
                    null,
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Charger fan clicking off too early",
            visualSymptom = "Battery voltage drops sharply under acceleration",
            isStrandedHazard = false
        ),

        VehicleIssue(
            id = "car_alternator_charging_failure",
            title = "Alternator Charging Failure & Serpentine Belt Slip",
            vehicleType = VehicleType.CAR,
            category = "Battery & Electrical",
            severity = Severity.HIGH,
            difficulty = Difficulty.DIFFICULT_PRO_RECOMMENDED,
            estimatedTime = "35-50 mins",
            summary = "Red Battery warning light stays illuminated on dashboard while driving, headlights grow dimmer, and vehicle is running purely on remaining battery capacity until engine dies.",
            symptoms = listOf(
                "Red Battery Icon stays solid ON while engine is running",
                "High-pitched screeching sound from engine bay on acceleration",
                "Infotainment screen flickers and power steering gets heavy",
                "Engine stalls completely once 12V battery is drained"
            ),
            commonCauses = listOf(
                "Loose, glazed, or snapped serpentine accessory belt",
                "Worn alternator carbon brushes or blown internal diode rectifier",
                "Faulty alternator voltage regulator outputting < 13.2V"
            ),
            safetyPrecautions = listOf(
                "Do NOT attempt long highway drives when battery light is ON (vehicle will stall in 15-30 mins)",
                "Keep hands clear of spinning belts and pulleys while engine runs"
            ),
            toolsNeeded = listOf(
                ToolItem("t37", "Digital Multimeter", true),
                ToolItem("t38", "Serpentine Belt Tensioner Tool / Long Wrench", true),
                ToolItem("t39", "10mm & 12mm Sockets", true)
            ),
            partsNeeded = listOf(
                PartItem("p23", "Serpentine Accessory Drive Belt (6PK)", "₹350 - ₹750 ($4.50 - $9)"),
                PartItem("p24", "Alternator Voltage Regulator / Brush Assembly", "₹650 - ₹1,400 ($8 - $17)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Test Alternator Output Voltage with Multimeter",
                    "Start engine. Connect multimeter across battery terminals. Healthy alternator should output 13.8V - 14.5V DC. If reading is < 12.6V, alternator is NOT charging.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Inspect Serpentine Belt Tension",
                    "Check the serpentine belt routing around alternator pulley. Press belt in middle - deflection should be 5mm-10mm. If loose or cracked, adjust tensioner or replace belt.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Check B+ Main Output Wire and Ground",
                    "Check the thick wire bolted to the back of the alternator. Ensure nut is tight and not corroded or melted.",
                    null,
                    "Disconnect battery ground before touching alternator B+ terminal.",
                    0
                ),
                FixStep(
                    4,
                    "Triage: Drive to Nearest Garage Before Battery Dies",
                    "Turn OFF headlights, AC, radio, and all electrical loads to preserve battery. Use Nearest Garages tab to navigate to nearest auto electrical workshop immediately.",
                    "Turn off all unnecessary electronics immediately.",
                    null,
                    0
                )
            ),
            obdCode = "P0562",
            audioSymptom = "Loud screeching belt slipping noise",
            visualSymptom = "Red battery warning light solid on dashboard",
            isStrandedHazard = true
        ),

        // ================= 2-WHEELER / MOTORBIKE & SCOOTER ISSUES =================
        VehicleIssue(
            id = "bike_drive_chain",
            title = "Loose, Slipped or Broken Drive Chain",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Drivetrain & Chain",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "15-25 mins",
            summary = "The motorcycle drive chain has slipped off the rear sprocket or is sagging heavily, causing metal slapping noises against the swingarm or loss of rear wheel drive.",
            symptoms = listOf(
                "Loud metal rattling / slapping noise when accelerating",
                "Engine revs up in gear but motorbike does not move",
                "Chain has more than 35mm vertical slack / sag",
                "Rear wheel locks up or chain bunches up at front countershaft sprocket"
            ),
            commonCauses = listOf(
                "Lack of regular chain lubrication and tension adjustment",
                "Worn master link clip popped off",
                "Severely hooked / worn rear sprocket teeth",
                "Loose rear axle nut allowing wheel to shift forward"
            ),
            safetyPrecautions = listOf(
                "Place motorcycle on the CENTER MAIN STAND on firm level ground",
                "ENGINE MUST BE COMPLETELY OFF and key removed",
                "NEVER touch chain while engine is running or idling in gear (Extreme finger amputation hazard)"
            ),
            toolsNeeded = listOf(
                ToolItem("tb1", "Rear Axle Spanners (usually 19mm & 24mm)", true),
                ToolItem("tb2", "10mm & 12mm Chain Adjuster Spanners", true),
                ToolItem("tb3", "Pliers for Master Link Clip", true),
                ToolItem("tb4", "Chain Lube Spray or Gear Oil (EP 90)", false)
            ),
            partsNeeded = listOf(
                PartItem("pb1", "Spare Drive Chain Master Link (428 / 520 size)", "₹50 - ₹100 ($0.70 - $1.20)"),
                PartItem("pb2", "Complete Chain & Sprocket Kit", "₹900 - ₹1,800 ($11 - $22)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Put on Center Stand and Inspect Slack",
                    "Place bike on center stand. Push the bottom run of chain up and down midway between sprockets. Normal slack is 20-30mm (about 1 inch).",
                    "If chain has fallen off, do not force the wheel. Loosen axle nut first.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Loosen Rear Axle Nut and Adjuster Locknuts",
                    "Loosen the large rear axle nut 1-2 turns. Loosen the locknuts on the chain tensioner bolts located on both left and right swingarm ends.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Refit Chain onto Sprockets",
                    "If chain slipped off, guide it over the bottom teeth of the rear sprocket while slowly rotating the rear wheel by hand counter-clockwise until chain seats fully.",
                    "Keep fingers away from sprocket pinch points.",
                    "Always rotate wheel forward by hand only.",
                    0
                ),
                FixStep(
                    4,
                    "Set Equal Alignment Notches and Tighten Axle",
                    "Turn both adjuster bolts equally clockwise until slack is 25mm. Verify notch alignment marks on both sides of swingarm match exactly. Torque axle nut firmly.",
                    "Apply chain lube evenly while spinning wheel by hand.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Heavy metallic chain slapping against swingarm, grinding clicks",
            visualSymptom = "Sagging chain touching swingarm, chain derailed from sprocket",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "bike_spark_starting_trouble",
            title = "Starting Trouble & Fouled Spark Plug (Kick / Self Start)",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Ignition & Fuel",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "10-20 mins",
            summary = "Motorbike or scooter starter motor cranks or kick lever is pressed repeatedly, but the engine refuses to start or only fires for a second before dying.",
            symptoms = listOf(
                "Kick starter has resistance but engine will not fire up",
                "Electric starter cranks normally with strong battery, but no combustion",
                "Strong smell of unburnt petrol near exhaust",
                "Engine stalls immediately when throttle is given"
            ),
            commonCauses = listOf(
                "Carbon-fouled or petrol-soaked spark plug electrode",
                "Water accumulated in carburetor float bowl after rain or pressure wash",
                "Kill switch (Engine Stop switch) accidentally flipped to OFF",
                "Fuel petcock tap turned to OFF or vacuum valve stuck"
            ),
            safetyPrecautions = listOf(
                "Ensure bike is in Neutral gear before attempting to start",
                "No open flames or smoking near fuel tank / spark plug hole",
                "Allow hot engine cylinder head to cool before removing plug"
            ),
            toolsNeeded = listOf(
                ToolItem("tb5", "Motorcycle Spark Plug Wrench (16mm)", true),
                ToolItem("tb6", "Sandpaper or Wire Brush", true),
                ToolItem("tb7", "Clean Cotton Cloth / Rag", true),
                ToolItem("tb8", "Flathead Screwdriver for Carb Drain Screw", false)
            ),
            partsNeeded = listOf(
                PartItem("pb3", "New Spark Plug (NGK / Bosch CR7E / CPR8EA)", "₹120 - ₹220 ($1.50 - $2.80)"),
                PartItem("pb4", "In-Line Transparent Fuel Filter", "₹40 - ₹80 ($0.50 - $1.00)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Verify Fuel Tap and Engine Kill Switch",
                    "Double check handlebar red Engine Kill switch is set to RUN. Ensure fuel tap is set to ON or RESERVE (RES) and fuel tank has petrol.",
                    "Many breakdowns are simply an accidental flip of the kill switch.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Remove and Clean Spark Plug",
                    "Pull off the black rubber spark plug cap. Use the 16mm plug spanner to unscrew the spark plug counter-clockwise. If wet with black fuel/oil, dry with cloth and scrub tip clean with sandpaper.",
                    "Check spark gap: gap between center and ground electrode should be approx 0.7mm (thickness of a thumbnail).",
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Check for Blue Spark Jump",
                    "Reinsert plug into cap. Hold the metal threaded body of the plug against the bare metal engine cylinder head. Turn ignition ON and kick once — watch for a crisp blue spark jumping across electrodes.",
                    "If spark is bright blue, ignition coil and CDI are good.",
                    "Do not hold the spark plug body directly with bare hands while cranking.",
                    0
                ),
                FixStep(
                    4,
                    "Drain Carburetor Bowl and Start Engine",
                    "If bike was in rain, loosen the drain screw at the bottom of the carburetor float chamber for 5 seconds to drain water droplets. Reinstall dry spark plug, tighten snugly, and start with choke ON.",
                    "Turn choke OFF once engine idles smoothly.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Rapid hollow kick cranking without combustion rumble",
            visualSymptom = "Black sooty or wet oily spark plug electrode tip",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "bike_clutch_throttle_cable",
            title = "Snapped Motorcycle Clutch or Throttle Cable",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Controls & Cables",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "15-20 mins",
            summary = "Left clutch lever or right throttle grip has gone completely limp with zero spring return. Rider is unable to disengage gear or accelerate the bike.",
            symptoms = listOf(
                "Clutch lever flops freely against handlebar with no tension",
                "Throttle grip spins without resistance and engine stays at idle",
                "Motorcycle lurches and stalls immediately when kicked into gear",
                "Frayed metal strands visible near lever barrel adjuster"
            ),
            commonCauses = listOf(
                "Internal steel cable wear and metal fatigue from repeated pulls",
                "Corrosion inside outer sheath due to water ingress",
                "Cable barrel nipple popped out of lever socket"
            ),
            safetyPrecautions = listOf(
                "Park motorcycle safely on side or center stand off the road",
                "Switch off ignition and allow hot exhaust pipes to cool before touching clutch arm on engine casing"
            ),
            toolsNeeded = listOf(
                ToolItem("tb9", "10mm & 12mm Open Spanners", true),
                ToolItem("tb10", "Pliers / Wire Cutter", true),
                ToolItem("tb11", "Flathead Screwdriver", true),
                ToolItem("tb12", "Chain Lube or 2T Oil for Cable Sheath", false)
            ),
            partsNeeded = listOf(
                PartItem("pb5", "Motorcycle Clutch Inner Wire with Nipple", "₹60 - ₹120 ($0.80 - $1.50)"),
                PartItem("pb6", "Universal Screw-on Cable Barrel Clamp / Solderless Nipple", "₹25 - ₹40 ($0.30 - $0.50)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Remove Broken Cable Strands",
                    "Line up the slots on the handlebar lever and barrel adjuster. Slide the broken top nipple out of the lever. At the engine clutch release arm, loosen the pinch clamp and pull the broken wire out.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Lube and Feed New Inner Wire",
                    "Apply a few drops of oil to the new inner wire. Feed it into the outer sheath from the top lever down towards the bottom engine arm.",
                    "Twist gently while pushing to prevent fraying.",
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Fasten Nipple and Set Freeplay",
                    "Seat top nipple into lever. At engine clutch arm, pull wire taut with pliers and tighten the 10mm screw clamp securely. Adjust lever freeplay to 2-3mm.",
                    "Ensure bike rolls freely in gear with clutch lever pulled before riding.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Cable snap sound, throttle twist clicks with no engine response",
            visualSymptom = "Limp clutch lever, loose throttle grip spinning freely",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "bike_ev_sidestand_sensor",
            title = "EV Scooter Motor Cutoff & Side-Stand Sensor Lock",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Electric & Powertrain",
            severity = Severity.HIGH,
            difficulty = Difficulty.EASY_DIY,
            estimatedTime = "10-15 mins",
            summary = "Electric scooter (e.g. Ola, Ather, TVS iQube, Chetak, Hero Vida) screen turns ON and displays battery state, but motor will not engage or dashboard shows 'Side Stand Down' even when stand is raised.",
            symptoms = listOf(
                "Dashboard screen shows 'Side Stand Down' or 'Stand Warning' with stand up",
                "Motor produces zero power when throttle is twisted in Drive mode",
                "Parking mode or Park lock cannot be disengaged",
                "Motor jerks or shuts down when going over speed bumps"
            ),
            commonCauses = listOf(
                "Mud, dirt, or grit jamming the magnetic side-stand reed switch sensor",
                "Loose side-stand sensor wiring coupler under bottom floorboard",
                "Sensor magnet misaligned due to bent side-stand bracket"
            ),
            safetyPrecautions = listOf(
                "Turn scooter ignition key or smart screen lock OFF before touching wiring",
                "Ensure scooter is securely on Center Stand so rear wheel can spin freely if tested"
            ),
            toolsNeeded = listOf(
                ToolItem("tb13", "Water Bottle / Clean Rag / Brush for Mud", true),
                ToolItem("tb14", "Electrical Insulation Tape", false),
                ToolItem("tb15", "10mm / 12mm Spanner for Stand Bracket", false)
            ),
            partsNeeded = listOf(
                PartItem("pb7", "Magnetic Side-Stand Sensor Switch", "₹180 - ₹350 ($2.20 - $4.20)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Put on Center Stand and Inspect Sensor Pivot",
                    "Put scooter on center stand. Look at the pivot joint of the side-stand. Clean away accumulated mud, pebbles, and grime around the magnetic sensor pill.",
                    "Most sensor failures are simply dried mud blocking the magnetic trigger.",
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Cycle the Side-Stand 5-10 Times",
                    "Firmly raise and lower the side-stand 5 to 10 times to let the internal return spring and magnet reseat properly against the sensor housing.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Emergency Bypass: Check Wire Coupler",
                    "If warning persists on highway, trace the 2-pin wire from the stand pivot upwards. Unplug and reseat the waterproof coupler firmly.",
                    "If sensor is broken, shorting the 2 signal pins with a small paperclip/wire enables emergency drive mode to reach the nearest EV garage.",
                    "Remember to raise side stand manually before riding if bypassed!",
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "Warning chime or horn beep when throttle is twisted",
            visualSymptom = "Side Stand Warning icon blinking on digital screen",
            isStrandedHazard = true
        ),

        VehicleIssue(
            id = "bike_disc_brake_jam",
            title = "Front Disc Brake Jam & Spongy Brake Lever",
            vehicleType = VehicleType.TWO_WHEELER,
            category = "Brakes & Safety",
            severity = Severity.HIGH,
            difficulty = Difficulty.MODERATE_DIY,
            estimatedTime = "20-30 mins",
            summary = "Front wheel is hard to push / jammed due to seized brake caliper pistons, or the front brake lever pulls all the way to the handlebar without stopping the bike.",
            symptoms = listOf(
                "Front wheel does not rotate freely and disc rotor becomes burning hot",
                "Brake lever feels spongy or sinks to the grip",
                "Squealing friction noise while riding without touching brakes",
                "Low brake fluid level in handlebar master cylinder sight glass"
            ),
            commonCauses = listOf(
                "Rust and brake dust seizing the caliper slide pins and pistons",
                "Air bubbles inside hydraulic brake fluid line",
                "Brake pads worn down to steel backing plate"
            ),
            safetyPrecautions = listOf(
                "DO NOT touch brake disc rotor immediately after riding (Extreme burn hazard)",
                "Brake fluid (DOT 3 / DOT 4) eats vehicle paint — wipe spills immediately with water"
            ),
            toolsNeeded = listOf(
                ToolItem("tb16", "8mm & 10mm Ring Spanners / Allen Hex Key (6mm/8mm)", true),
                ToolItem("tb17", "Flathead Screwdriver for Pad Separation", true),
                ToolItem("tb18", "Clean Water & Cloth for Caliper Wash", true)
            ),
            partsNeeded = listOf(
                PartItem("pb8", "Motorcycle Front Disc Brake Pads (Pair)", "₹180 - ₹450 ($2.20 - $5.50)"),
                PartItem("pb9", "DOT 4 Hydraulic Brake Fluid (100ml)", "₹80 ($1.00)")
            ),
            steps = listOf(
                FixStep(
                    1,
                    "Inspect Fluid Level & Rotor Cleanliness",
                    "Check the round sight glass on the right handlebar master cylinder reservoir. Fluid should be amber, not dark brown or empty.",
                    null,
                    null,
                    0
                ),
                FixStep(
                    2,
                    "Wash Caliper and Free Stuck Slide Pins",
                    "Pour water over the brake caliper assembly to wash away caked road dirt. Insert a flathead screwdriver gently between the brake pads and pry outwards to push the pistons back into the caliper body.",
                    "Do not damage pad friction material with aggressive prying.",
                    null,
                    0
                ),
                FixStep(
                    3,
                    "Pump Lever and Test Wheel Spin",
                    "Pump the front brake lever 5 to 6 times until firm pressure returns. Lift the front wheel and spin it by hand — it should spin smoothly without binding.",
                    "If wheel remains seized, visit nearest Two-Wheeler Garage for caliper piston seal rebuild.",
                    null,
                    0
                )
            ),
            obdCode = null,
            audioSymptom = "High-pitched brake scraping screech, dragging wheel sound",
            visualSymptom = "Brake rotor burning hot, front wheel resistance when pushed",
            isStrandedHazard = true
        )
    )

    fun getAllIssues(): List<VehicleIssue> = issues

    fun getIssuesByVehicleType(type: VehicleType): List<VehicleIssue> {
        if (type == VehicleType.ALL) return issues
        return issues.filter { it.vehicleType == type || it.vehicleType == VehicleType.ALL }
    }

    fun getIssueById(id: String): VehicleIssue? = issues.find { it.id == id }

    fun searchIssues(query: String, vehicleType: VehicleType = VehicleType.ALL): List<VehicleIssue> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return getIssuesByVehicleType(vehicleType)

        return getIssuesByVehicleType(vehicleType).filter { issue ->
            issue.title.lowercase().contains(q) ||
            issue.category.lowercase().contains(q) ||
            issue.summary.lowercase().contains(q) ||
            issue.symptoms.any { it.lowercase().contains(q) } ||
            issue.obdCode?.lowercase()?.contains(q) == true ||
            issue.commonCauses.any { it.lowercase().contains(q) }
        }
    }

    fun findByObdCode(code: String): VehicleIssue? {
        val cleanCode = code.trim().uppercase()
        return issues.find { it.obdCode?.uppercase() == cleanCode }
    }

    fun getStrandedIssues(): List<VehicleIssue> = issues.filter { it.isStrandedHazard }
}
