package it.polimi.ingsw.PSP50.GodsList;

import it.polimi.ingsw.PSP50.*;

import java.util.ArrayList;

public class Athena extends God {
    private final Gods name = Gods.ATHENA;

    // Athena doesn't need to use Power because she uses normal move and build.
    @Override
    public ArrayList<Space> power(Player player) {
        return null;
    }
}
