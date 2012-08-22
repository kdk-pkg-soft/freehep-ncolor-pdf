// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

/**
 * Defines the tags for SWF.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SWFTagSet.java 9979 2006-11-27 22:51:07Z duns $
 */
public class SWFTagSet extends SWFSpriteTagSet {

    public SWFTagSet(int version) {

        super(version);

        // The following tags do NOT exist:
        // 3, 16, 25, 27, 29, 30, 31, 40-42, 44, 47, 49-55 and 63

        // Initialize tags
        // Version 1
        if (version >= 1) {
            addTag(new DefineShape()); // 2
            addTag(new DefineBits()); // 6
            addTag(new DefineButton()); // 7
            addTag(new JPEGTables()); // 8
            addTag(new SetBackgroundColor()); // 9
            addTag(new DefineFont()); // 10
            addTag(new DefineText()); // 11
            addTag(new DefineFontInfo()); // 13
            addTag(new DefineSound()); // 14

            // added in version 8, just skipped in earlier versions
            addTag(new FileAttributes()); // 69
        }

        // Version 2
        if (version >= 2) {
            addTag(new DefineButtonSound()); // 17
            addTag(new DefineBitsLossless()); // 20
            addTag(new DefineBitsJPEG2()); // 21
            addTag(new DefineShape2()); // 22
            addTag(new DefineButtonCxform()); // 23
            addTag(new Protect()); // 24
        }

        // Version 3
        if (version >= 3) {
            addTag(new DefineShape3()); // 32
            addTag(new DefineText2()); // 33
            addTag(new DefineButton2()); // 34
            addTag(new DefineBitsJPEG3()); // 35
            addTag(new DefineBitsLossless2()); // 36
            addTag(new DefineSprite()); // 39
            addTag(new SoundStreamHead2()); // 45
            addTag(new DefineMorphShape()); // 46
            addTag(new DefineFont2()); // 48
        }

        // version 4
        if (version >= 4) {
            addTag(new DefineEditText()); // 37
            addTag(new DefineMovie()); // 38
        }

        // version 5
        if (version >= 5) {
            addTag(new ExportAssets()); // 56
            addTag(new ImportAssets()); // 57
        }

        if (version == 5) {
            addTag(new EnableDebugger()); // 58
        }

        // version 6
        if (version >= 6) {
            addTag(new DoInitAction()); // 59
            addTag(new DefineVideoStream()); // 60
            addTag(new VideoFrame()); // 61
            addTag(new DefineFontInfo2()); // 62
            addTag(new EnableDebugger2()); // 64
        }
        
        // version 7
        if (version >= 7) {
        	// addTag(new ScriptLimits());	// 65
        	// addTag(new SetTabIndex()); 	// 66
        }
        
        // version 8
        if (version >= 8) {
            // NOTE: FileAttributes (69) under version 1.
            // addTag(new PlaceObject3());	// 70
        	// addTag(new Import2()); 		// 71
        	// addTag(new DefineFontAlignZoned());	// 73
        	// addTag(new CSMTextSettings());	// 74
        	// addTag(new DefineFont3());	// 75
        	// addTag(new Metadata());	// 77
        	// addTag(new DefineScalingGrid()); // 78
        	addTag(new DefineShape4());	// 83
        	// addTag(new DefineMorphShape2());	// 84
        	
        }
    }
}
