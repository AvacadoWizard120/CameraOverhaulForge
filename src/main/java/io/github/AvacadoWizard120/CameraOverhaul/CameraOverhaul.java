package io.github.AvacadoWizard120.CameraOverhaul;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = CameraOverhaul.MODID,
	name = CameraOverhaul.NAME,
	version = CameraOverhaul.VERSION
)
public class CameraOverhaul {
	public static final String MODID = "cameraoverhaul";
	public static final String NAME = "Camera Overhaul";
	public static final String VERSION = "2.0";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
}
