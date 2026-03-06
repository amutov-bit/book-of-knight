package com.pgd.game;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.pgd.game.actors.Button;
import com.pgd.game.base.IButtonCallback;

public class GameTexts extends Group {

	BookOfKnight game;
	
	public String path = "";
	
	public String balanceReal = "";
	public String balanceDemo = "";
	public String bet	 	  = "";
	public String win	 	  = "";
	public String line	 	  = "";
	
	public String goodLuck	 	  = "";
	public String autoStatusTxt	 	  = "";
	public String menuBetTitle	 	  = "";
	public String menuBetTotalBet 	  = "";
	public String menuBetMaxBet  	  = "";
	public String menuBetLines  	  = "";
	public String autoPlayTitle  	  = "";
	public String autoNumbersTxtLabel = "";
	public String autoPlayStart		  = "";
	
	public String rulesGamePercent     = "";
	public String rulesGamePercentBuyFree     = "";
	public String rulesGamePercentBuyHold     = "";
	
	public String turboSpinsTitle	  = "";

	public String holdSpin		   = "";
	public String placeBets		   = "";

	public String maxWinReachedTitle  	  = "";
	public String maxWinReachedCongrats  	  = "";
	public String maxWinReachedYouHave  	  = "";
	public String maxWinReachedEnd  	  = "";
	
	// FREE SPINS
	public String buttonClaim  	      = "";
	public String freespinsTitle      = "";
	public String freespinsCongrats   = "";
	public String freespinsGet		  = "";
	public String freespinsNumber     = "";
	public String freespinsTxt        = "";
	
	public String freespinsLeft		    = "";
	public String freespinsleftNumber	= "";
	public String freespinsTotalWin	    = "";
	public String freespinsTotalNumber  = "";
	public String freespinsBet		    = "";
	public String freespinsBetNumber	= "";

	public String freespinsCongratsTitle  = "";
	public String freespinsCongratsYou	  = "";
	public String freespinsCongratsWon	  = "";
	public String freespinsCongratsNumber = "";
	public String freespinsCongratsTxt	  = "";
	
	public String insertCreditTitle  	  = "";
	public String insertCreditTxt	  	  = "";
	public String insertCredits	  	  = "";

	public String boostTxt  	  = "";
	public String boostBet  	  = "";

	public String soundTitle  	  = "";
	public String soundYes  	  = "";
	public String soundNo	  	  = "";
	
	
	public String helpHistory   = "";
	public String helpPay		= "";
	public String helpRules     = "";
	public String helpSettings  = "";
	public String helpPages     = "";
	
	
	public String settingsSoundOn	   = "";
	public String settingsGameSounds   = "";
	public String settingsMusic        = "";
	public String settingsTitle        = "";
	public String settingsVolume 	   = "";
	public String settingsLobby 	   = "";
	public String settingsSkipIntro    = "";
	public String settingsTurboSpin    = "";
	public String settingsTurboSpinTxt = "";

	public String settingsSkipScreen   = "";

	public String splashTitle 	  	   = "";
	public String splashTxt 	  	   = "";
	public String splashTxt2 	  	   = "";
	public String splashTxt3 	  	   = "";
	
	public String splashSecTitle 	     = "";
	public String splashSecTitleWild 	 = "";
	public String splashSecTitleSpecial  = "";
	public String splashSecTxt 	  	     = "";
	public String splashSecTxt2 	     = "";
	public String splashSecTxt3 	     = "";
	
	public String splashTxtPort 	   = "";
	public String splashTxt2Port	   = "";
	public String splashTxt3Port	   = "";
	
	public String rulesTitle 	  	   = "";
	public String rulesMAXbet 	  	   = "";
	public String rulesMINbet 	  	   = "";
	public String rulesInterface 	   = "";
	public String rulesAutoplay 	   = "";
	public String rulesBetMenu 	   	   = "";
	public String rulesSettings	   	   = "";
	public String rulesInterfacePort   = "";
	public String rulesAutoplayPort	   = "";
	public String rulesBetMenuPort 	   = "";
	public String rulesSettingsPort    = "";
	
	public String rulesLines    	   = "";

	public String rulesLinesPort       = "";

	public String rulesLines2    	   = "";
	public String rulesLinesPort2       = "";

	public String rulesUnfinished      = "";
	public String rulesUnfinishedPort  = "";

	public String rulesMaxWin      = "";
	public String rulesMaxWinPort  = "";

	public String rulesBuyBonus      = "";
	public String rulesBuyBonus2      = "";
	
	public String paylinesTitle 	   = "";
	public String paylinesTxt 		   = "";
	public String paylinesTxt2 		   = "";
	public String paylinesTxtPort	   = "";
	public String paylinesTxtPort2	   = "";
	public String paylinesLine 	   	   = "";

	public String paytableTitle 	   = "";
	public String paytableWild 	   	   = "";
	public String paytableScatter 	   = "";

	public String startTap	 	   	   = "";
	public String startWinUp 	   	   = "";
	public String startTxt   	       = "";

	public String pressStart	 	   = "";
	public String pressAnywhere 	   = "";
	public String youWon	   	       = "";
	public String congrats	  		   = "";
	public String expandingSymbol	   = "";
	public String freeGameBook 	       = "";
	public String freeGame 	    	   = "";
	public String freeGameLeft   	   = "";
	public String repinsLeft   	       = "";
	public String freeGameTxt  	       = "";
	
	public String rulesAddFg =  "";
	
	public String buyTxt	  	       = "";
	public String buyBonusTxt	  	   = "";
	public String holdAndWinTxt	  	   = "";
	
	XmlReader xmlReader;
	FileHandle file;
	Element rootE;
	Element languageElement;
	Element child;
	String nameAttribute;
	
	public GameTexts (BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets(){
		game.ondemandAssetManager.load("texts/lang.xml", File.class);
	}
	
	public void commitAssets(){
        // Load the XML file from the assets folder
        xmlReader = new XmlReader();
        file =  game.manager.get("texts/lang.xml", File.class).getFile();
        try {
			rootE = xmlReader.parse(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setTexts(String languageCode) throws IOException{

        // Find the language element based on the language code
        languageElement = null;
        for (int i = 0; i < rootE.getChildCount(); i++) {
            child = rootE.getChild(i);
            nameAttribute = child.getAttribute("name");
            if (nameAttribute != null && nameAttribute.equals(languageCode)) {
                // Language found, read data from this language
                languageElement = child;
                break; // Exit the loop once the language is found
            }
        }
        
        if (languageElement != null) {
            // Read data from the language element
        	path		        = languageElement.getChildByName("path").getText();
        	
        	startTap            = languageElement.getChildByName("startTap").getText();
        	
        	expandingSymbol     = languageElement.getChildByName("expandingSymbol").getText();
        	expandingSymbol     = expandingSymbol.replace("\\n", "\n");
        	
        	
        	buttonClaim			= languageElement.getChildByName("buttonClaim").getText();
        	freespinsTitle		= languageElement.getChildByName("freespinsTitle").getText();
        	freespinsCongrats	= languageElement.getChildByName("freespinsCongrats").getText();
        	freespinsGet		= languageElement.getChildByName("freespinsGet").getText();
        	freespinsNumber		= languageElement.getChildByName("freespinsNumber").getText();
        	freespinsTxt		= languageElement.getChildByName("freespinsTxt").getText();
        	
        	freespinsLeft		 = languageElement.getChildByName("freespinsLeft").getText();
        	freespinsleftNumber	 = languageElement.getChildByName("freespinsleftNumber").getText();
        	freespinsTotalWin	 = languageElement.getChildByName("freespinsTotalWin").getText();
        	freespinsTotalNumber = languageElement.getChildByName("freespinsTotalNumber").getText();
        	freespinsBet		 = languageElement.getChildByName("freespinsBet").getText();
        	freespinsBetNumber	 = languageElement.getChildByName("freespinsBetNumber").getText();
        	
        	freespinsCongratsTitle  = languageElement.getChildByName("freespinsCongratsTitle").getText();
        	freespinsCongratsTitle 	= freespinsCongratsTitle.replace("\\n", "\n");
        	freespinsCongratsYou	= languageElement.getChildByName("freespinsCongratsYou").getText();
        	freespinsCongratsWon	= languageElement.getChildByName("freespinsCongratsWon").getText();
        	freespinsCongratsNumber = languageElement.getChildByName("freespinsCongratsNumber").getText();
        	freespinsCongratsTxt	= languageElement.getChildByName("freespinsCongratsTxt").getText();
        	
        	rulesAddFg	= languageElement.getChildByName("rulesAddFg").getText();
        	rulesAddFg 	= rulesAddFg.replace("\\n", "\n");
        	
        	
        	holdAndWinTxt       = languageElement.getChildByName("holdAndWinTxt").getText();
        	buyBonusTxt    	    = languageElement.getChildByName("buyBonusTxt").getText();
        	buyTxt       	    = languageElement.getChildByName("buyTxt").getText();
        	freeGameTxt         = languageElement.getChildByName("freeGameTxt").getText();
        	repinsLeft   	    = languageElement.getChildByName("repinsLeft").getText();
        	freeGameLeft        = languageElement.getChildByName("freeGameLeft").getText();
        	pressAnywhere       = languageElement.getChildByName("pressAnywhere").getText();
        	pressStart      	= languageElement.getChildByName("pressStart").getText();
        	youWon      	    = languageElement.getChildByName("youWon").getText();
        	congrats      	    = languageElement.getChildByName("congrats").getText();

        	freeGameBook      	= languageElement.getChildByName("freeGameBook").getText();
        	freeGameBook    	= freeGameBook.replace("\\n", "\n");

        	freeGame      		= languageElement.getChildByName("freeGame").getText();
        	freeGame    		= freeGameBook.replace("\\n", "\n");
        	
        	startWinUp          = languageElement.getChildByName("startWinUp").getText();
        	startTxt	        = languageElement.getChildByName("startTxt").getText();
        	startTxt      		= startTxt.replace("\\n", "\n");

        	maxWinReachedTitle  	= languageElement.getChildByName("maxWinReachedTitle").getText();
        	maxWinReachedTitle      = maxWinReachedTitle.replace("\\n", "\n");
        	maxWinReachedCongrats   = languageElement.getChildByName("maxWinReachedCongrats").getText();
        	maxWinReachedYouHave    = languageElement.getChildByName("maxWinReachedYouHave").getText();
        	maxWinReachedEnd  		= languageElement.getChildByName("maxWinReachedEnd").getText();
        	maxWinReachedEnd        = maxWinReachedEnd.replace("\\n", "\n");
        	 
        	insertCredits          	   = languageElement.getChildByName("insertCredits").getText();
        	insertCreditTitle          = languageElement.getChildByName("insertCreditTitle").getText();
        	insertCreditTxt	           = languageElement.getChildByName("insertCreditTxt").getText();
        	insertCreditTxt            = insertCreditTxt.replace("\\n", "\n");
        	
        	soundTitle         = languageElement.getChildByName("soundTitle").getText();
        	soundYes	       = languageElement.getChildByName("soundYes").getText();
        	soundNo	           = languageElement.getChildByName("soundNo").getText();
        	
        	boostTxt            = languageElement.getChildByName("boostTxt").getText();
        	boostTxt            = boostTxt.replace("\\n", "\n");
        	
        	boostBet            = languageElement.getChildByName("boostBet").getText();
        	
            balanceReal         = languageElement.getChildByName("balanceReal").getText();
            balanceDemo         = languageElement.getChildByName("balanceDemo").getText();
            bet  	            = languageElement.getChildByName("bet").getText();
            win     	        = languageElement.getChildByName("win").getText();
            line     	        = languageElement.getChildByName("line").getText();
            goodLuck     	    = languageElement.getChildByName("goodLuck").getText();
            autoStatusTxt     	= languageElement.getChildByName("autoStatusTxt").getText();
            menuBetTitle     	= languageElement.getChildByName("menuBetTitle").getText();
            menuBetTotalBet    	= languageElement.getChildByName("menuBetTotalBet").getText();
            
            menuBetMaxBet     	= languageElement.getChildByName("menuBetMaxBet").getText();
            menuBetMaxBet 		= menuBetMaxBet.replace("\\n", "\n");
            
            menuBetLines     	= languageElement.getChildByName("menuBetLines").getText();
            autoPlayTitle     	= languageElement.getChildByName("autoPlayTitle").getText();
            autoNumbersTxtLabel = languageElement.getChildByName("autoNumbersTxtLabel").getText();
            autoPlayStart     	= languageElement.getChildByName("autoPlayStart").getText();
            
            turboSpinsTitle     = languageElement.getChildByName("turboSpinsTitle").getText();
            turboSpinsTitle 	= turboSpinsTitle.replace("\\n", "\n");

            holdSpin     		= languageElement.getChildByName("holdSpin").getText();
            placeBets     		= languageElement.getChildByName("placeBets").getText();

            helpHistory        	= languageElement.getChildByName("helpHistory").getText();
            
            
            helpPay		     	= languageElement.getChildByName("helpPay").getText();
            helpPay 			= helpPay.replace("\\n", "\n");
            
            helpRules        	= languageElement.getChildByName("helpRules").getText();
            helpSettings     	= languageElement.getChildByName("helpSettings").getText();
            helpPages	     	= languageElement.getChildByName("helpPages").getText();
            
            paytableTitle		= languageElement.getChildByName("paytableTitle").getText();
            paytableTitle 		= paytableTitle.replace("\\n", "\n");

            paytableWild		= languageElement.getChildByName("paytableWild").getText();
            paytableWild 		= paytableWild.replace("\\n", "\n");

            paytableScatter		= languageElement.getChildByName("paytableScatter").getText();
            paytableScatter 	= paytableScatter.replace("\\n", "\n");
            
            splashTitle			= languageElement.getChildByName("splashTitle").getText();
            splashTxt			= languageElement.getChildByName("splashTxt").getText();
            splashTxt 			=  splashTxt.replace("\\n", "\n");
            splashTxt2			= languageElement.getChildByName("splashTxt2").getText();
            splashTxt2 			=  splashTxt2.replace("\\n", "\n");
            splashTxt3			= languageElement.getChildByName("splashTxt3").getText();
            splashTxt3 			=  splashTxt3.replace("\\n", "\n");

            splashSecTitle		  = languageElement.getChildByName("splashSecTitle").getText();
            splashSecTitleWild	  = languageElement.getChildByName("splashSecTitleWild").getText();
            splashSecTitleSpecial = languageElement.getChildByName("splashSecTitleSpecial").getText();
            splashSecTxt		  = languageElement.getChildByName("splashSecTxt").getText();
            splashSecTxt 		  = splashSecTxt.replace("\\n", "\n");
            splashSecTxt2		  = languageElement.getChildByName("splashSecTxt2").getText();
            splashSecTxt2 		  = splashSecTxt2.replace("\\n", "\n");
            splashSecTxt3		  = languageElement.getChildByName("splashSecTxt3").getText();
            splashSecTxt3 		  = splashSecTxt3.replace("\\n", "\n");
            
            splashTxtPort		= languageElement.getChildByName("splashTxtPort").getText();
            splashTxtPort 		=  splashTxtPort.replace("\\n", "\n");
            splashTxt2Port		= languageElement.getChildByName("splashTxt2Port").getText();
            splashTxt2Port 		=  splashTxt2Port.replace("\\n", "\n");

//            splashTxt2Port		= languageElement.getChildByName("splashTxt2Port").getText();
//            splashTxt2Port 		=  splashTxt2Port.replace("\\n", "\n");
            
            rulesTitle			= languageElement.getChildByName("rulesTitle").getText();
            rulesMAXbet			= languageElement.getChildByName("rulesMAXbet").getText();
            rulesMINbet			= languageElement.getChildByName("rulesMINbet").getText();
            rulesInterface		= languageElement.getChildByName("rulesInterface").getText();
            rulesInterface 		= rulesInterface.replace("\\n", "\n");
            rulesAutoplay		= languageElement.getChildByName("rulesAutoplay").getText();
            rulesAutoplay 		= rulesAutoplay.replace("\\n", "\n");
            rulesBetMenu		= languageElement.getChildByName("rulesBetMenu").getText();
            rulesBetMenu 		= rulesBetMenu.replace("\\n", "\n");
            rulesSettings		= languageElement.getChildByName("rulesSettings").getText();
            rulesSettings 		= rulesSettings.replace("\\n", "\n");

            rulesGamePercent		= languageElement.getChildByName("rulesGamePercent").getText();
            
            if (languageElement.getChildByName("rulesGamePercentBuyFree") != null) {
            	rulesGamePercentBuyFree		= languageElement.getChildByName("rulesGamePercentBuyFree").getText();
            }
            
            if (languageElement.getChildByName("rulesGamePercentBuyHold") != null) {
            	rulesGamePercentBuyHold		= languageElement.getChildByName("rulesGamePercentBuyHold").getText();
            }
            
            rulesLines			= languageElement.getChildByName("rulesLines").getText();
            rulesLines 			= rulesLines.replace("\\n", "\n");
            
            if(game.languageCode == "TUR" || game.languageCode == "BG" || game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "PL" || game.languageCode == "RUS" || game.languageCode == "CZE" || game.languageCode == "FRA"){
	            rulesLines2			= languageElement.getChildByName("rulesLines2").getText();
	            rulesLines2 		= rulesLines2.replace("\\n", "\n");
            }
            
            rulesUnfinished		= languageElement.getChildByName("rulesUnfinished").getText();
            rulesUnfinished 	= rulesUnfinished.replace("\\n", "\n");

            rulesMaxWin		= languageElement.getChildByName("rulesMaxWin").getText();
            rulesMaxWin 	= rulesMaxWin.replace("\\n", "\n");

	        rulesBuyBonus	= languageElement.getChildByName("rulesBuyBonus").getText();
	        rulesBuyBonus 	= rulesBuyBonus.replace("\\n", "\n");

	        if(game.languageCode == "BG"){
	            rulesBuyBonus2	= languageElement.getChildByName("rulesBuyBonus2").getText();
	            rulesBuyBonus2 	= rulesBuyBonus2.replace("\\n", "\n");
	        }
            
            rulesInterfacePort		= languageElement.getChildByName("rulesInterfacePort").getText();
            rulesInterfacePort 		= rulesInterfacePort.replace("\\n", "\n");
            rulesAutoplayPort		= languageElement.getChildByName("rulesAutoplayPort").getText();
            rulesAutoplayPort 		= rulesAutoplayPort.replace("\\n", "\n");
            rulesBetMenuPort		= languageElement.getChildByName("rulesBetMenuPort").getText();
            rulesBetMenuPort 		= rulesBetMenuPort.replace("\\n", "\n");
            rulesSettingsPort		= languageElement.getChildByName("rulesSettingsPort").getText();
            rulesSettingsPort 		= rulesSettingsPort.replace("\\n", "\n");

            rulesLinesPort			= languageElement.getChildByName("rulesLinesPort").getText();
            rulesLinesPort 			= rulesLinesPort.replace("\\n", "\n");
            
            if(game.languageCode == "BG" || game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "TUR" || game.languageCode == "PL" || game.languageCode == "CZE" || game.languageCode == "RUS"){
	            rulesLinesPort2			= languageElement.getChildByName("rulesLinesPort2").getText();
	            rulesLinesPort2 			= rulesLinesPort2.replace("\\n", "\n");
            }
            rulesUnfinishedPort		= languageElement.getChildByName("rulesUnfinishedPort").getText();
            rulesUnfinishedPort 	= rulesUnfinishedPort.replace("\\n", "\n");

            rulesMaxWinPort		= languageElement.getChildByName("rulesMaxWinPort").getText();
            rulesMaxWinPort 	= rulesMaxWinPort.replace("\\n", "\n");
            
            paylinesTitle		= languageElement.getChildByName("paylinesTitle").getText();
            paylinesTitle 		= paylinesTitle.replace("\\n", "\n");
            
            paylinesLine		= languageElement.getChildByName("paylinesLine").getText();
            
            paylinesTxt			= languageElement.getChildByName("paylinesTxt").getText();
            paylinesTxt 		=  paylinesTxt.replace("\\n", "\n");

            paylinesTxtPort		= languageElement.getChildByName("paylinesTxtPort").getText();
            paylinesTxtPort 	=  paylinesTxtPort.replace("\\n", "\n");

            if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA"|| game.languageCode == "PL" || game.languageCode == "BG"){
            	paylinesTxt2		= languageElement.getChildByName("paylinesTxt2").getText();
            	paylinesTxt2 		=  paylinesTxt2.replace("\\n", "\n");
            	paylinesTxtPort2	= languageElement.getChildByName("paylinesTxtPort2").getText();
            	paylinesTxtPort2 	=  paylinesTxtPort2.replace("\\n", "\n");
            }            

            settingsSoundOn		= languageElement.getChildByName("settingsSoundOn").getText();
            settingsSoundOn 	= settingsSoundOn.replace("\\n", "\n");
            
            settingsGameSounds  = languageElement.getChildByName("settingsGameSounds").getText();
            settingsGameSounds 	= settingsGameSounds.replace("\\n", "\n");
            
            settingsMusic       = languageElement.getChildByName("settingsMusic").getText();
            settingsTitle       = languageElement.getChildByName("settingsTitle").getText();
            settingsVolume      = languageElement.getChildByName("settingsVolume").getText();
            settingsVolume 		= settingsVolume.replace("\\n", "\n");
            
            settingsLobby       = languageElement.getChildByName("settingsLobby").getText();
            
            settingsSkipIntro   = languageElement.getChildByName("settingsSkipIntro").getText();
            settingsSkipIntro 	= settingsSkipIntro.replace("\\n", "\n");
            
            settingsTurboSpin    = languageElement.getChildByName("settingsTurboSpin").getText();
            settingsTurboSpin 	 = settingsTurboSpin.replace("\\n", "\n");
            settingsTurboSpinTxt = languageElement.getChildByName("settingsTurboSpinTxt").getText();
            settingsSkipScreen   = languageElement.getChildByName("settingsSkipScreen").getText();
            
            settingsSkipScreen 	=  settingsSkipScreen.replace("\\n", "\n");
            
        } else {
            Gdx.app.debug("GameTexts", "Language not found: " + languageCode);
        }

	}
	
}
