function validateSurvey() {
    var lat = "";
    if (document.getElementById("lat").firstChild != null)
        lat = document.getElementById("lat").firstChild.data;
    var latNS = "";
    if (document.getElementById("latNS").firstChild != null)
        latNS = document.getElementById("latNS").firstChild.data;
    var lng = "";
    if (document.getElementById("lng").firstChild != null)
        lng = document.getElementById("lng").firstChild.data;
    var latEW = "";
    if (document.getElementById("lngEW").firstChild != null)
        lngEW = document.getElementById("lngEW").firstChild.data;
    var acc = "";
    if (document.getElementById("acc").firstChild != null)
        acc = document.getElementById("acc").firstChild.data;

    //Do not allow if we don't know where we are
    if (lat == "" || latNS == "" || lng == "" || lngEW == "" || acc == "") {
        showError("errorMissingGPS");
        return false;
    }
    return validateSurveyWithoutGPS();

}

// Check all pages for valid entries
function validateSurveyWithoutGPS() {
    return (checkStateHomesteadSprayed() && checkStateFirstSprayer() && checkStateSecondSprayer() && checkStateSecondSprayer2()
            && checkStateDDT() && checkStateDDT2() && checkStateDDT3() && checkStateDDT4()
            && checkStateKOrthrine() && checkStateKOrthrine2() && checkStateKOrthrine3() && checkStateKOrthrine4()
            && checkStateSp2DDT() && checkStateSp2DDT2() && checkStateSp2DDT3() && checkStateSp2DDT4()
            && checkStateSp2KOrthrine() && checkStateSp2KOrthrine2() && checkStateSp2KOrthrine3() && checkStateSp2KOrthrine4()
            && checkStateUnsprayed() && checkStateUnsprayed2() 
            && checkStateForemanName() );
}

//Displays results in summary page.
function displaySurvey() {
//    if (!validateSurveyWithoutGPS())
//        return;
    var homesteadSprayedOn = document.getElementById("homesteadSprayedOn").checked;
    var homesteadSprayedOff = document.getElementById("homesteadSprayedOff").checked;
    var sprayerID = document.getElementById("sprayerID").value;
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    var DDTSprayedRooms = document.getElementById("DDTSprayedRoomsInput").value;
    var DDTSprayedShelters = document.getElementById("DDTSprayedSheltersInput").value;
    var refilledDDT = document.getElementById("refilledDDT").checked;
    var noRefilledDDT = document.getElementById("noRefilledDDT").checked;
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;
    var kOrthrineSprayedRooms = document.getElementById("kOrthrineSprayedRoomsInput").value;
    var kOrthrineSprayedShelters = document.getElementById("kOrthrineSprayedSheltersInput").value;
    var refilledKOrthrine= document.getElementById("refilledKOrthrine").checked;
    var noRefilledKOrthrine = document.getElementById("noRefilledKOrthrine").checked;
    
    var sprayer2On= document.getElementById("secondSprayerOn").checked;
    var sprayer2Off = document.getElementById("secondSprayerOff").checked;
    var sprayer2ID = document.getElementById("sprayer2ID").value;
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    var Sp2DDTSprayedRooms = document.getElementById("Sp2DDTSprayedRoomsInput").value;
    var Sp2DDTSprayedShelters = document.getElementById("Sp2DDTSprayedSheltersInput").value;
    var refilledSp2DDT = document.getElementById("refilledSp2DDT").checked;
    var noRefilledSp2DDT = document.getElementById("noRefilledSp2DDT").checked;
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    var Sp2kOrthrineSprayedRooms = document.getElementById("Sp2kOrthrineSprayedRoomsInput").value;
    var Sp2kOrthrineSprayedShelters = document.getElementById("Sp2kOrthrineSprayedSheltersInput").value;
    var refilledSp2KOrthrine= document.getElementById("refilledSp2KOrthrine").checked;
    var noRefilledSp2KOrthrine = document.getElementById("noRefilledSp2KOrthrine").checked;
    
    var unsprayedRooms = document.getElementById("unsprayedRoomsInput").value;
    var unsprayedShelters = document.getElementById("unsprayedSheltersInput").value;

    var foremanName = document.getElementById("foremanName").value;


    if (homesteadSprayedOff) {
        sprayerID = "N/A";
        DDTSprayedRooms = "N/A";
        DDTSprayedShelters = "N/A";
        refilledDDT = "N/A";
        DDTSprayedRooms = "N/A";
        DDTSprayedShelters = "N/A";
        refilledDDT = "N/A";
        kOrthrineSprayedRooms = "N/A";
        kOrthrineSprayedShelters = "N/A";
        refilledKOrthrine = "N/A";

        sprayer2ID = "N/A";
        Sp2DDTSprayedRooms = "N/A";
        Sp2DDTSprayedShelters = "N/A";
        refilledSp2DDT = "N/A";
        Sp2DDTSprayedRooms = "N/A";
        Sp2DDTSprayedShelters = "N/A";
        refilledSp2DDT = "N/A";
        Sp2kOrthrineSprayedRooms = "N/A";
        Sp2kOrthrineSprayedShelters = "N/A";
        refilledSp2KOrthrine = "N/A";
    } else {
        if (sprayer2Off) {
            sprayer2ID = "N/A";
            Sp2DDTSprayedRooms = "N/A";
            Sp2DDTSprayedShelters = "N/A";
            refilledSp2DDT = "N/A";
            Sp2kOrthrineSprayedRooms = "N/A";
            Sp2kOrthrineSprayedShelters = "N/A";
            refilledSp2KOrthrine = "N/A";
        }
        if (DDTOff) {
            DDTSprayedRooms = "N/A";
            DDTSprayedShelters = "N/A";
            refilledDDT = "N/A";
        } else
            refilledDDT = bool2Str(refilledDDT);
        if (kOrthrineOff) {
            kOrthrineSprayedRooms = "N/A";
            kOrthrineSprayedShelters = "N/A";
            refilledKOrthrine = "N/A";
        } else
            refilledKOrthrine = bool2Str(refilledKOrthrine);
        
        if (Sp2DDTOff) {
            Sp2DDTSprayedRooms = "N/A";
            Sp2DDTSprayedShelters = "N/A";
            refilledSp2DDT = "N/A";
        } else
            refilledSp2DDT = bool2Str(refilledSp2DDT);
        if (Sp2kOrthrineOff) {
            Sp2kOrthrineSprayedRooms = "N/A";
            Sp2kOrthrineSprayedShelters = "N/A";
            refilledSp2KOrthrine = "N/A";
        } else
            refilledSp2KOrthrine = bool2Str(refilledSp2KOrthrine);
    }
    
    
    $("#finalHomesteadSprayed").text(bool2Str(homesteadSprayedOn).toString());
    $("#finalFirstID").text(sprayerID.toString());
    $("#finalDDT").text(bool2Str(DDTOn).toString());
    $("#finalDDTSprayedRooms").text(DDTSprayedRooms.toString());
    $("#finalDDTSprayedShelters").text(DDTSprayedShelters.toString());
    $("#finalRefillDDT").text(refilledDDT.toString());
    $("#finalKOrthrine").text(bool2Str(kOrthrineOn).toString());
    $("#finalKOrthrineSprayedRooms").text(kOrthrineSprayedRooms.toString());
    $("#finalKOrthrineSprayedShelters").text(kOrthrineSprayedShelters.toString());
    $("#finalRefillKOrthrine").text(refilledKOrthrine.toString());

    $("#finalSecondID").text(sprayer2ID.toString());
    $("#finalSp2DDT").text(bool2Str(Sp2DDTOn).toString());
    $("#finalSp2DDTSprayedRooms").text(Sp2DDTSprayedRooms.toString());
    $("#finalSp2DDTSprayedShelters").text(Sp2DDTSprayedShelters.toString());
    $("#finalRefillSp2DDT").text(refilledSp2DDT.toString());
    $("#finalSp2KOrthrine").text(bool2Str(Sp2kOrthrineOn).toString());
    $("#finalSp2KOrthrineSprayedRooms").text(Sp2kOrthrineSprayedRooms.toString());
    $("#finalSp2KOrthrineSprayedShelters").text(Sp2kOrthrineSprayedShelters.toString());
    $("#finalRefillSp2KOrthrine").text(refilledSp2KOrthrine.toString());
    
    $("#finalUnsprayedRooms").text(unsprayedRooms.toString());
    $("#finalUnsprayedShelters").text(unsprayedShelters.toString());

    $("#finalForemanName").text(foremanName.toString());
}

function submitSurvey() {
//	if (!validateSurvey())
//		return;
	var lat = document.getElementById("lat").firstChild.data;
	var latNS = document.getElementById("latNS").firstChild.data;
	var lng = document.getElementById("lng").firstChild.data;
	var lngEW = document.getElementById("lngEW").firstChild.data;
    var acc = document.getElementById("acc").firstChild.data;

    var homesteadSprayedOn = document.getElementById("homesteadSprayedOn").checked;
    var homesteadSprayedOff = document.getElementById("homesteadSprayedOff").checked;

	var sprayerID = document.getElementById("sprayerID").value;
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    var DDTSprayedRooms = document.getElementById("DDTSprayedRoomsInput").value;
    var DDTSprayedShelters = document.getElementById("DDTSprayedSheltersInput").value;
    var refilledDDT = document.getElementById("refilledDDT").checked;
    var noRefilledDDT = document.getElementById("noRefilledDDT").checked;
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;
    var kOrthrineSprayedRooms = document.getElementById("kOrthrineSprayedRoomsInput").value;
    var kOrthrineSprayedShelters = document.getElementById("kOrthrineSprayedSheltersInput").value;
    var refilledKOrthrine= document.getElementById("refilledKOrthrine").checked;
    var noRefilledKOrthrine = document.getElementById("noRefilledKOrthrine").checked;

    var sprayer2On= document.getElementById("secondSprayerOn").checked;
    var sprayer2Off = document.getElementById("secondSprayerOff").checked;
    var sprayer2ID = document.getElementById("sprayer2ID").value;
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    var Sp2DDTSprayedRooms = document.getElementById("Sp2DDTSprayedRoomsInput").value;
    var Sp2DDTSprayedShelters = document.getElementById("Sp2DDTSprayedSheltersInput").value;
    var refilledSp2DDT = document.getElementById("refilledSp2DDT").checked;
    var noRefilledSp2DDT = document.getElementById("noRefilledSp2DDT").checked;
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    var Sp2kOrthrineSprayedRooms = document.getElementById("Sp2kOrthrineSprayedRoomsInput").value;
    var Sp2kOrthrineSprayedShelters = document.getElementById("Sp2kOrthrineSprayedSheltersInput").value;
    var refilledSp2KOrthrine= document.getElementById("refilledSp2KOrthrine").checked;
    var noRefilledSp2KOrthrine = document.getElementById("noRefilledSp2KOrthrine").checked;

    var unsprayedRooms = document.getElementById("unsprayedRoomsInput").value;
    var unsprayedShelters = document.getElementById("unsprayedSheltersInput").value;

    var foremanName = document.getElementById("foremanName").value;
    
    if (homesteadSprayedOff) {
        sprayerID = "";
        DDTSprayedRooms = "";
        DDTSprayedShelters = "";
        refilledDDT = "";
        DDTSprayedRooms = "";
        DDTSprayedShelters = "";
        refilledDDT = "";
        kOrthrineSprayedRooms = "";
        kOrthrineSprayedShelters = "";
        refilledKOrthrine = "";
        
        sprayer2ID = "";
        Sp2DDTSprayedRooms = "";
        Sp2DDTSprayedShelters = "";
        refilledSp2DDT = "";
        Sp2DDTSprayedRooms = "";
        Sp2DDTSprayedShelters = "";
        refilledSp2DDT = "";
        Sp2kOrthrineSprayedRooms = "";
        Sp2kOrthrineSprayedShelters = "";
        refilledSp2KOrthrine = "";
    } else {
        if (sprayer2Off) {
            sprayer2ID = "";
            Sp2DDTSprayedRooms = "";
            Sp2DDTSprayedShelters = "";
            refilledSp2DDT = "";
            Sp2kOrthrineSprayedRooms = "";
            Sp2kOrthrineSprayedShelters = "";
            refilledSp2KOrthrine = "";
        }
        
        if (DDTOff) {
            DDTSprayedRooms = "";
            DDTSprayedShelters = "";
            refilledDDT = "";
        }
        if (kOrthrineOff) {
            kOrthrineSprayedRooms = "";
            kOrthrineSprayedShelters = "";
            refilledKOrthrine = "";
        }
        
        if (Sp2DDTOff) {
            Sp2DDTSprayedRooms = "";
            Sp2DDTSprayedShelters = "";
            refilledSp2DDT = "";
        }
        if (Sp2kOrthrineOff) {
            Sp2kOrthrineSprayedRooms = "";
            Sp2kOrthrineSprayedShelters = "";
            refilledSp2KOrthrine = "";
        }
    }

    resultString = lat + "||" + latNS + "||" + lng + "||" + lngEW + "||" + acc
    		+ "||" + homesteadSprayedOn
            + "||" + sprayerID 
    		+ "||" + DDTOn + "||" + DDTSprayedRooms + "||" + DDTSprayedShelters + "||" + refilledDDT
    		+ "||" + kOrthrineOn + "||" + kOrthrineSprayedRooms + "||" + kOrthrineSprayedShelters + "||" + refilledKOrthrine
            + "||" + sprayer2ID
            + "||" + Sp2DDTOn + "||" + Sp2DDTSprayedRooms + "||" + Sp2DDTSprayedShelters + "||" + refilledSp2DDT
            + "||" + Sp2kOrthrineOn + "||" + Sp2kOrthrineSprayedRooms + "||" + Sp2kOrthrineSprayedShelters + "||" + refilledSp2KOrthrine
    		+ "||" + unsprayedRooms + "||" + unsprayedShelters
    		+ "||" + foremanName;
//    alert(resultString);
    
    window.location = rootLoc() + "#pageFinished";
    Android.submitToast(resultString);
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for the showing and hiding html elements
///////////////////////////////////////////////////////////////////////////////////
function hide(id) {
    var e = document.getElementById(id);
    e.style.display="none";
}

function show(id) {
    var e = document.getElementById(id);
    e.style.display="inline";
}

function showError(id) {
    var e = document.getElementById(id);
    e.style.display="inline";
    e.style.color="red";
}


///////////////////////////////////////////////////////////////////////////////////
///// Methods for homestead sprayed?
///////////////////////////////////////////////////////////////////////////////////
function verifyHomesteadSprayed() {
    hide("errorToggleHomesteadSprayed");
    var homesteadSprayedOn = document.getElementById("homesteadSprayedOn").checked;
    var homesteadSprayedOff = document.getElementById("homesteadSprayedOff").checked;
    
    if (checkStateHomesteadSprayed() && homesteadSprayedOff)
        window.location = rootLoc() + "#pageUnsprayed1";
    else if (checkStateHomesteadSprayed())
        window.location = rootLoc() + "#pageSprayer";
}

function checkStateHomesteadSprayed() {
    hide("errorToggleHomesteadSprayed");
    var homesteadSprayedOn = document.getElementById("homesteadSprayedOn").checked;
    var homesteadSprayedOff = document.getElementById("homesteadSprayedOff").checked;
    
    if (!homesteadSprayedOn && !homesteadSprayedOff) {
        showError("errorToggleHomesteadSprayed")
        return false;
    }
    return true;
}




///////////////////////////////////////////////////////////////////////////////////
///// Methods for the first sprayer
///////////////////////////////////////////////////////////////////////////////////
function verifyFirstSprayer() {
    hide("errorMissingSprayer1");
    if (checkStateFirstSprayer())
        window.location = rootLoc() + "#pageDDT1";
}

function checkStateFirstSprayer() {
    var sprayerID = document.getElementById("sprayerID").value;
    if (sprayerID == "-none-") {
        showError("errorMissingSprayer1");
        return false;
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for the second sprayer - 1
///////////////////////////////////////////////////////////////////////////////////
function verifySecondSprayer() {
    hide("errorToggleSprayer2");
    var secondSprayerOn = document.getElementById("secondSprayerOn").checked;
    var secondSprayerOff = document.getElementById("secondSprayerOff").checked;

    if (checkStateSecondSprayer() && secondSprayerOff)
        window.location = rootLoc() + "#pageUnsprayed1";
    else if (checkStateSecondSprayer())
        window.location = rootLoc() + "#pageSprayer2-2";
}

function checkStateSecondSprayer() {
    hide("errorToggleSprayer2");
    var secondSprayerOn = document.getElementById("secondSprayerOn").checked;
    var secondSprayerOff = document.getElementById("secondSprayerOff").checked;

    if (!secondSprayerOn && !secondSprayerOff) {
        showError("errorToggleSprayer2")
        return false;
    }
    return true;
}

function backFromSecondSprayer() {
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;
    if (kOrthrineOn)
        window.location = rootLoc() + "#pageKOrthrine4";
    else if (kOrthrineOff)
        window.location = rootLoc() + "#pageKOrthrine1";
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for the second sprayer - 2
///////////////////////////////////////////////////////////////////////////////////
function verifySecondSprayer2() {
    if (checkStateSecondSprayer2())
        window.location = rootLoc() + "#pageSp2DDT1";
}

function checkStateSecondSprayer2() {
    hide("errorMissingSprayer2");
    hide("errorMatchingSprayers");
    var secondSprayerOn = document.getElementById("secondSprayerOn").checked;
    var sprayerID = document.getElementById("sprayerID").value;
    var sprayer2ID = document.getElementById("sprayer2ID").value;

    if (secondSprayerOn && sprayer2ID == "-none-")
        showError("errorMissingSprayer2");
    else if (secondSprayerOn && (sprayer2ID == sprayerID))
        showError("errorMatchingSprayers");
    else
        return true;
    return false;
}




///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 1
///////////////////////////////////////////////////////////////////////////////////
function verifyDDT() {
    hide("errorToggleDDT");
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    
    if (checkStateDDT() && DDTOn)
        window.location = rootLoc() + "#pageDDT2";
    else if (checkStateDDT() && DDTOff)
        window.location = rootLoc() + "#pageKOrthrine1";
}

function checkStateDDT() {
    hide("errorToggleDDT");
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    
    if (!DDTOn && !DDTOff) {
        showError("errorToggleDDT");
        return false;
    }
    return true;
}

function backFromDDT() {
    var secondSprayerOn = document.getElementById("secondSprayerOn").checked;
    var secondSprayerOff = document.getElementById("secondSprayerOff").checked;
    if (secondSprayerOn)
        window.location = rootLoc() + "#pageSprayer2-2";
    else if (secondSprayerOff)
        window.location = rootLoc() + "#pageSprayer2-1";
}



///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 1  (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2DDT() {
    hide("errorToggleSp2DDT");
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    
    if (checkStateSp2DDT() && Sp2DDTOn)
        window.location = rootLoc() + "#pageSp2DDT2";
    else if (checkStateSp2DDT() && Sp2DDTOff)
        window.location = rootLoc() + "#pageSp2KOrthrine1";
}

function checkStateSp2DDT() {
    hide("errorToggleSp2DDT");
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    
    if (!Sp2DDTOn && !Sp2DDTOff) {
        showError("errorToggleSp2DDT");
        return false;
    }
    return true;
}




///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 2
///////////////////////////////////////////////////////////////////////////////////
function verifyDDT2() {
    if (checkStateDDT2())
        window.location = rootLoc() + "#pageDDT3";
}

function checkStateDDT2() {
    hide("errorMissingDDTInfo1");
    hide("errorInvalidDDTInfo1");
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    var DDTSprayedRooms = document.getElementById("DDTSprayedRoomsInput").value;
    if (DDTOn && (DDTSprayedRooms == ""))
        showError("errorMissingDDTInfo1");
    else if (DDTOn && isNaN(parseInt(DDTSprayedRooms)))
        showError("errorInvalidDDTInfo1");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 2 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2DDT2() {
    if (checkStateSp2DDT2())
        window.location = rootLoc() + "#pageSp2DDT3";
}

function checkStateSp2DDT2() {
    hide("errorMissingSp2DDTInfo1");
    hide("errorInvalidSp2DDTInfo1");
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    var Sp2DDTSprayedRooms = document.getElementById("Sp2DDTSprayedRoomsInput").value;
    if (Sp2DDTOn && (Sp2DDTSprayedRooms == ""))
        showError("errorMissingSp2DDTInfo1");
    else if (Sp2DDTOn && isNaN(parseInt(Sp2DDTSprayedRooms)))
        showError("errorInvalidSp2DDTInfo1");
    else
        return true;
    return false;
}


///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 3
///////////////////////////////////////////////////////////////////////////////////
function verifyDDT3() {
    if (checkStateDDT3())
        window.location = rootLoc() + "#pageDDT4";
}

function checkStateDDT3() {
    hide("errorMissingDDTShelterInfo");
    hide("errorInvalidDDTShelterInfo");
    hide("errorInvalidDDTShelterInfo2");
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    var DDTSprayedShelters = document.getElementById("DDTSprayedSheltersInput").value;
    var DDTSprayedRooms = document.getElementById("DDTSprayedRoomsInput").value;
    if (DDTOn && (DDTSprayedShelters == ""))
        showError("errorMissingDDTShelterInfo");
    else if (DDTOn && isNaN(parseInt(DDTSprayedShelters)))
        showError("errorInvalidDDTShelterInfo");
    else if (DDTOn && (parseInt(DDTSprayedShelters)==0) && (parseInt(DDTSprayedRooms)==0))
        showError("errorInvalidDDTShelterInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 3 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2DDT3() {
    if (checkStateSp2DDT3())
        window.location = rootLoc() + "#pageSp2DDT4";
}

function checkStateSp2DDT3() {
    hide("errorMissingSp2DDTShelterInfo");
    hide("errorInvalidSp2DDTShelterInfo");
    hide("errorInvalidSp2DDTShelterInfo2");
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    var Sp2DDTSprayedShelters = document.getElementById("Sp2DDTSprayedSheltersInput").value;
    var Sp2DDTSprayedRooms = document.getElementById("Sp2DDTSprayedRoomsInput").value;
    if (Sp2DDTOn && (Sp2DDTSprayedShelters == ""))
        showError("errorMissingSp2DDTShelterInfo");
    else if (Sp2DDTOn && isNaN(parseInt(Sp2DDTSprayedShelters)))
        showError("errorInvalidSp2DDTShelterInfo");
    else if (Sp2DDTOn && (parseInt(Sp2DDTSprayedShelters)==0) && (parseInt(Sp2DDTSprayedRooms)==0))
        showError("errorInvalidSp2DDTShelterInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 4
///////////////////////////////////////////////////////////////////////////////////
function verifyDDT4() {
    if (checkStateDDT4())
        window.location = rootLoc() + "#pageKOrthrine1";
}

function checkStateDDT4() {
    hide("errorMissingDDTInfo2");
    var DDTOn = document.getElementById("DDTOn").checked;
    var refilledDDT = document.getElementById("refilledDDT").checked;
    var noRefilledDDT = document.getElementById("noRefilledDDT").checked;
    if (DDTOn && (!refilledDDT && !noRefilledDDT)) {
        showError("errorMissingDDTInfo2");
        return false;
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for DDT - 4 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2DDT4() {
    if (checkStateSp2DDT4())
        window.location = rootLoc() + "#pageSp2KOrthrine1";
}

function checkStateSp2DDT4() {
    hide("errorMissingSp2DDTInfo2");
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var refilledSp2DDT = document.getElementById("refilledSp2DDT").checked;
    var noRefilledSp2DDT = document.getElementById("noRefilledSp2DDT").checked;
    if (Sp2DDTOn && (!refilledSp2DDT && !noRefilledSp2DDT)) {
        showError("errorMissingSp2DDTInfo2");
        return false;
    }
    return true;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 1
///////////////////////////////////////////////////////////////////////////////////
function verifyKOrthrine() {
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;

    if (checkStateKOrthrine() && kOrthrineOn)
        window.location = rootLoc() + "#pageKOrthrine2";
    else if (checkStateKOrthrine() && kOrthrineOff)
        window.location = rootLoc() + "#pageSprayer2-1";
}

function checkStateKOrthrine() {
    hide("errorToggleKOrthrine");
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;

    if (!kOrthrineOn && !kOrthrineOff) {
        showError("errorToggleKOrthrine");
        return false;
    }
    return true;
}

function backFromKOrthrine() {
    var DDTOn = document.getElementById("DDTOn").checked;
    var DDTOff = document.getElementById("DDTOff").checked;
    if (DDTOn)
        window.location = rootLoc() + "#pageDDT4";
    else if (DDTOff)
        window.location = rootLoc() + "#pageDDT1";
}



///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 1 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2KOrthrine() {
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    
    if (checkStateSp2KOrthrine() && Sp2kOrthrineOn)
        window.location = rootLoc() + "#pageSp2KOrthrine2";
    else if (checkStateKOrthrine() && kOrthrineOff)
        window.location = rootLoc() + "#pageUnsprayed1";
}

function checkStateSp2KOrthrine() {
    hide("errorToggleSp2KOrthrine");
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    
    if (!Sp2kOrthrineOn && !Sp2kOrthrineOff) {
        showError("errorToggleSp2KOrthrine");
        return false;
    }
    return true;
}

function backFromSp2KOrthrine() {
    var Sp2DDTOn = document.getElementById("Sp2DDTOn").checked;
    var Sp2DDTOff = document.getElementById("Sp2DDTOff").checked;
    if (Sp2DDTOn)
        window.location = rootLoc() + "#pageSp2DDT4";
    else if (Sp2DDTOff)
        window.location = rootLoc() + "#pageSp2DDT1";
}




///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 2
///////////////////////////////////////////////////////////////////////////////////
function verifyKOrthrine2() {
    if (checkStateKOrthrine2())
        window.location = rootLoc() + "#pageKOrthrine3";
}

function checkStateKOrthrine2() {
    hide("errorMissingKOrthrineInfo1");
    hide("errorInvalidKOrthrineInfo1");
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;
    var kOrthrineSprayedRooms = document.getElementById("kOrthrineSprayedRoomsInput").value;
    if (kOrthrineOn && (kOrthrineSprayedRooms == ""))
        showError("errorMissingKOrthrineInfo1");
    else if (kOrthrineOn && isNaN(parseInt(kOrthrineSprayedRooms)))
        showError("errorInvalidKOrthrineInfo1");
    else
        return true;
    return false;
}


///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 2 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2KOrthrine2() {
    if (checkStateSp2KOrthrine2())
        window.location = rootLoc() + "#pageSp2KOrthrine3";
}

function checkStateSp2KOrthrine2() {
    hide("errorMissingSp2KOrthrineInfo1");
    hide("errorInvalidSp2KOrthrineInfo1");
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    var Sp2kOrthrineSprayedRooms = document.getElementById("Sp2kOrthrineSprayedRoomsInput").value;
    if (Sp2kOrthrineOn && (Sp2kOrthrineSprayedRooms == ""))
        showError("errorMissingSp2KOrthrineInfo1");
    else if (Sp2kOrthrineOn && isNaN(parseInt(Sp2kOrthrineSprayedRooms)))
        showError("errorInvalidSp2KOrthrineInfo1");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 3
///////////////////////////////////////////////////////////////////////////////////
function verifyKOrthrine3() {
    if (checkStateKOrthrine3())
        window.location = rootLoc() + "#pageKOrthrine4";
}

function checkStateKOrthrine3() {
    hide("errorMissingKOrthrineShelterInfo");
    hide("errorInvalidKOrthrineShelterInfo");
    hide("errorInvalidKOrthrineShelterInfo2");
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var kOrthrineOff = document.getElementById("kOrthrineOff").checked;
    var kOrthrineSprayedShelters = document.getElementById("kOrthrineSprayedSheltersInput").value;
    var kOrthrineSprayedRooms = document.getElementById("kOrthrineSprayedRoomsInput").value;
    if (kOrthrineOn && (kOrthrineSprayedShelters == ""))
        showError("errorMissingKOrthrineShelterInfo");
    else if (kOrthrineOn && isNaN(parseInt(kOrthrineSprayedShelters)))
        showError("errorInvalidKOrthrineShelterInfo");
    else if (kOrthrineOn && (parseInt(kOrthrineSprayedShelters)==0) && (parseInt(kOrthrineSprayedRooms)==0))
        showError("errorInvalidKOrthrineShelterInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 3 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2KOrthrine3() {
    if (checkStateSp2KOrthrine3())
        window.location = rootLoc() + "#pageSp2KOrthrine4";
}

function checkStateSp2KOrthrine3() {
    hide("errorMissingSp2KOrthrineShelterInfo");
    hide("errorInvalidSp2KOrthrineShelterInfo");
    hide("errorInvalidSp2KOrthrineShelterInfo2");
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var Sp2kOrthrineOff = document.getElementById("Sp2kOrthrineOff").checked;
    var Sp2kOrthrineSprayedShelters = document.getElementById("Sp2kOrthrineSprayedSheltersInput").value;
    var Sp2kOrthrineSprayedRooms = document.getElementById("Sp2kOrthrineSprayedRoomsInput").value;
    if (Sp2kOrthrineOn && (Sp2kOrthrineSprayedShelters == ""))
        showError("errorMissingSp2KOrthrineShelterInfo");
    else if (Sp2kOrthrineOn && isNaN(parseInt(Sp2kOrthrineSprayedShelters)))
        showError("errorInvalidSp2KOrthrineShelterInfo");
    else if (Sp2kOrthrineOn && (parseInt(Sp2kOrthrineSprayedShelters)==0) && (parseInt(Sp2kOrthrineSprayedRooms)==0))
        showError("errorInvalidSp2KOrthrineShelterInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 4
///////////////////////////////////////////////////////////////////////////////////
function verifyKOrthrine4() {
    if (checkStateKOrthrine4())
        window.location = rootLoc() + "#pageSprayer2-1";
}

function checkStateKOrthrine4() {
    hide("errorMissingKOrthrineInfo2");
    var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
    var refilledKOrthrine = document.getElementById("refilledKOrthrine").checked;
    var noRefilledKOrthrine = document.getElementById("noRefilledKOrthrine").checked;
    if (kOrthrineOn && (!refilledKOrthrine && !noRefilledKOrthrine))
        showError("errorMissingKOrthrineInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for K-Orthrine - 4 (second sprayer)
///////////////////////////////////////////////////////////////////////////////////
function verifySp2KOrthrine4() {
    if (checkStateSp2KOrthrine4())
        window.location = rootLoc() + "#pageUnsprayed1";
}

function checkStateSp2KOrthrine4() {
    hide("errorMissingSp2KOrthrineInfo2");
    var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
    var refilledSp2KOrthrine = document.getElementById("refilledSp2KOrthrine").checked;
    var noRefilledSp2KOrthrine = document.getElementById("noRefilledSp2KOrthrine").checked;
    if (Sp2kOrthrineOn && (!refilledSp2KOrthrine && !noRefilledSp2KOrthrine))
        showError("errorMissingSp2KOrthrineInfo2");
    else
        return true;
    return false;
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for Unsprayed - 1
///////////////////////////////////////////////////////////////////////////////////
function verifyUnsprayed() {
    if (checkStateUnsprayed())
        window.location = rootLoc() + "#pageUnsprayed2";
}

function checkStateUnsprayed() {
    hide("errorMissingUnsprayedInfo");
    var unsprayedRooms = document.getElementById("unsprayedRoomsInput").value;
    if (unsprayedRooms == "")
        showError("errorMissingUnsprayedInfo");
    else if (isNaN(parseInt(unsprayedRooms)))
        showError("errorMissingUnsprayedInfo");
    else
        return true;
}

function backFromUnsprayed() {
    var homesteadSprayedOn = document.getElementById("homesteadSprayedOn").checked;
    var homesteadSprayedOff = document.getElementById("homesteadSprayedOff").checked;
    if (homesteadSprayedOn) {
        var secondSprayerOn = document.getElementById("secondSprayerOn").checked;
        if (secondSprayerOn) {
            var Sp2kOrthrineOn = document.getElementById("Sp2kOrthrineOn").checked;
            if (Sp2kOrthrineOn)
                window.location = rootLoc() + "#pageSp2KOrthrine4";
            else
                window.location = rootLoc() + "#pageSp2KOrthrine1";
        } else {
            var kOrthrineOn = document.getElementById("kOrthrineOn").checked;
            if (kOrthrineOn)
                window.location = rootLoc() + "#pageKOrthrine4";
            else
                window.location = rootLoc() + "#pageKOrthrine1";
        }
    }
    else if (homesteadSprayedOff)
        window.location = rootLoc() + "#pageSprayed";
}

///////////////////////////////////////////////////////////////////////////////////
///// Methods for Unsprayed - 2
///////////////////////////////////////////////////////////////////////////////////
function verifyUnsprayed2() {
    if (checkStateUnsprayed2())
        window.location = rootLoc() + "#pageForeman";
}

function checkStateUnsprayed2() {
    hide("errorMissingUnsprayedShelterInfo");
    hide("errorInvalidUnsprayedShelterInfo");
    var unsprayedShelters = document.getElementById("unsprayedSheltersInput").value;
    if (unsprayedShelters == "")
        showError("errorMissingUnsprayedShelterInfo");
    else if (isNaN(parseInt(unsprayedShelters)))
        showError("errorInvalidUnsprayedShelterInfo");
    else
        return true;
    return false;
}

function backFromUnsprayed2() {
    window.location = rootLoc() + "#pageUnsprayed1";
}


///////////////////////////////////////////////////////////////////////////////////
///// Methods for Foreman
///////////////////////////////////////////////////////////////////////////////////
function verifyForemanName() {
    hide("errorMissingForemanName");
    if (checkStateForemanName()) {
        displaySurvey();
        window.location = rootLoc() + "#pageSummary";
    }
}

function checkStateForemanName() {
    var foremanName = document.getElementById("foremanName").value;
    if (foremanName == "-none-") {
        showError("errorMissingForemanName");
        return false;
    }
    return true;
}




///////////////////////////////////////////////////////////////////////////////////
///// Miscellaneous methods
///////////////////////////////////////////////////////////////////////////////////
function bool2Str(preconvert) {
    if (preconvert)
        return "yes";
    return "no";
}

function rootLoc() {
    return window.location.toString().split("#")[0];
}
