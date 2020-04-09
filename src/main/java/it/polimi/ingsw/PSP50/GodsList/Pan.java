package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Pan extends God {
    private final Gods name = Gods.PAN;

    // Pan doesn't need to use Power because he uses normal move and build.
    @Override
    public ArrayList<Space> power(Player player) {
        return null;

    }
}
