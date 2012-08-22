package org.freehep.application;
import java.awt.Component;
 
/**
 * HelpInterface is the interface which users of the help system deal with.
 * Isolate application from JavaHelp so can run without jh.jar
 * @version $Id: HelpService.java 8584 2006-08-10 23:06:37Z duns $
 * @author Peter Armstrong
 * @author Tony Johnson
 */

public interface HelpService 
{
	/**
	 * Shows the specified JavaHelp topic according to the display parameters provided.
	 * @param helpTopicTarget The JavaHelp XML target name which maps to the .html page in the map file
	 * @param navigatorView The string specifying which of the three views to have visible
    * @param owner The Component to own the help window
	 */
	void showHelpTopic(String helpTopicTarget, String navigatorView, Component owner);
}
