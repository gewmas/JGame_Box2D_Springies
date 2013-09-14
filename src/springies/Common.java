package springies;

public interface Common {
    public static final double WALL_MARGIN = 10;
    public static final double WALL_THICKNESS = 10;

    public static final int WALL_CID = 2;
    public static final int MASS_CID = 3;
    public static final int FIXEDMASS_CID = 4;
    public static final int SPRING_CID = 5;

    //Parser
    public static final String NODES_KEYWORD = "nodes";
    public static final String LINKS_KEYWORD = "links";
    public static final String GRAVITY_KEYWORD = "gravity";
    public static final String VISCOSITY_KEYWORD = "viscosity";
    public static final String CENTERMASS_KEYWORD = "centermass";
    public static final String WALL_KEYWORD = "wall";

    public static final String MASS_KEYWORD = "mass";
    public static final String FIXEDMASS_KEYWORD = "fixedmass";
    public static final String FIXED_KEYWORD = "fixed";
    public static final String MUSCLE_KEYWORD = "muscle";
    public static final String SPRING_KEYWORD = "spring";

    public static final String MASS_ID = "id";
    public static final String MASS_MASS = "mass";
    public static final String MASS_VX = "vx";
    public static final String MASS_VY = "vy";
    public static final String MASS_X = "x";
    public static final String MASS_Y = "y";

    public static final String SPRING_A = "a";
    public static final String SPRING_B = "b";
    public static final String SPRING_CONSTANT = "constant";
    public static final String SPRING_RESTLENGTH = "restlength";
    public static final String MUSCLE_AMPLITUDE = "amplitude";

    public static final String MAGNITUDE = "magnitude";
    public static final String GRAVITY_DIRECTION = "direction";
    public static final String EXPONENT = "exponent";
}
