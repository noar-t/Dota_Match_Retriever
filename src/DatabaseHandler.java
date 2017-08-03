import java.sql.*;

/**
 * Created by noah on 7/31/17.
 */
public class DatabaseHandler {
    private final String mDatabasePath = "jdbc:sqlite:test.db";
    private Connection database;
    private boolean mDatabasePreexist;

    public DatabaseHandler() {
        try {
            database = DriverManager.getConnection(mDatabasePath);
            if (database != null) {

                DatabaseMetaData meta = database.getMetaData();

                // check if database is already initialized
                ResultSet rs = meta.getTables(null, null, "matches", null);
                mDatabasePreexist = rs.next();
                rs = meta.getTables(null, null, "players", null);
                mDatabasePreexist = mDatabasePreexist && rs.next();
                rs = meta.getTables(null, null, "players_data", null);
                mDatabasePreexist = rs.next();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created and or an existing database has been connected.");

                if (mDatabasePreexist == false) {
                    String sql = "CREATE TABLE IF NOT EXISTS matches (\n"
                            + "	match_id INTEGER NOT NULL PRIMARY KEY,\n"
                            + "	radiant_win INTEGER NOT NULL,\n"
                            + "	radiant_score INTEGER NOT NULL,\n"
                            + "	dire_score INTEGER NOT NULL,\n"
                            + "	duration INTEGER\n"
                            + ");";

                    Statement stmt = database.createStatement();
                    stmt.execute(sql);


                    sql = "CREATE TABLE IF NOT EXISTS players_data (\n"
                            + "	player_id INTEGER NOT NULL,\n"
                            + "	match_id INTEGER NOT NULL,\n" // foreign key? references dotaMatches matchId
                            + "	player_slot INTEGER NOT NULL,\n"
                            + "	hero_id INTEGER NOT NULL,\n" // add future hero table
                            + "	radiant_hero INTEGER NOT NULL,\n" // bool to designate team
                            + "	item_slot0 INTEGER,\n"
                            + "	item_slot1 INTEGER,\n"
                            + "	item_slot2 INTEGER,\n"
                            + "	item_slot3 INTEGER,\n"
                            + "	item_slot4 INTEGER,\n"
                            + "	item_slot5 INTEGER,\n"
                            + "	back_slot0 INTEGER,\n"
                            + "	back_slot1 INTEGER,\n"
                            + "	back_slot2 INTEGER\n,"
                            + "     FOREIGN KEY (player_id) REFERENCES players(player_id)\n," // link player ids to player table
                            + "     FOREIGN KEY (match_id) REFERENCES matches(match_id)\n"
                            + ");";
                    stmt.execute(sql);

                    sql = "CREATE TABLE IF NOT EXISTS players (\n"
                            + "	player_name TEXT,\n"
                            + "	player_id INTEGER NOT NULL PRIMARY KEY \n"
                            + ");";
                    stmt.execute(sql);
                }
            }

        }
        catch (SQLException e) {
            System.out.println("Problem establishing connection with database");
            System.out.println(e.getMessage());
        }
    }

    public boolean databasePreexist() {
        return mDatabasePreexist;
    }

    public void databaseAddMatch(Match match) throws SQLException {
        String sql = "INSERT INTO matches(match_id, radiant_win, radiant_score, dire_score) VALUES(?,?,?,?)";

        PreparedStatement pstmt = database.prepareStatement(sql);
        pstmt.setLong(1, match.getMatchId());
        pstmt.setInt(2, match.isRadiantWin() ? 1 : 0);
        pstmt.setInt(3, match.getRadiantScore());
        pstmt.setInt(4, match.getDireScore());
        pstmt.executeUpdate();
    }

    public void databaseAddPlayerData(long matchId, Player player) throws SQLException {
        String sql = "INSERT INTO players_data(player_id, match_id, hero_id, item_slot0," +
                " item_slot1, item_slot2, item_slot3, item_slot4, item_slot5," +
                " back_slot0, back_slot1, back_slot2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = database.prepareStatement(sql);
        pstmt.setLong(1, player.getAccountId());
        pstmt.setLong(2, matchId);
        pstmt.setInt(3, player.getHeroId());
        int items[] = player.getItemSlots();
        pstmt.setInt(4, items[0]);
        pstmt.setInt(5, items[1]);
        pstmt.setInt(6, items[2]);
        pstmt.setInt(7, items[3]);
        pstmt.setInt(8, items[4]);
        pstmt.setInt(9, items[5]);
        int backpack[] = player.getBackPackSlots();
        pstmt.setInt(10, backpack[0]);
        pstmt.setInt(11, backpack[1]);
        pstmt.setInt(12, backpack[2]);
        pstmt.executeUpdate();
    }

    public void databaseAddPlayer(String playerName, long playerId) throws SQLException {
        String sql = "INSERT INTO matches(player_name, player_id) VALUES(?,?,?)";

        PreparedStatement pstmt = database.prepareStatement(sql);
        pstmt.setString(1, playerName);
        pstmt.setLong(2, playerId);
        pstmt.executeUpdate();
    }

    public Player databaseRetrievePlayerData(long player_id, long match_id) throws SQLException {
        String sql = "SELECT * FROM players_data WHERE player_id = ? AND match_id = ?";
        PreparedStatement pstmt  = database.prepareStatement(sql);

        pstmt.setLong(1, player_id);
        pstmt.setLong(2, match_id);


        long accountId = 0;
        int heroId = 0;
        int[] itemSlots = new int[6];
        int[] backPackSlots = new int[3];

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            accountId = rs.getLong("player_id");
            heroId = rs.getInt("hero_id");
            for (int i = 0; i < 6; i++)
                itemSlots[i] = rs.getInt("item_slot" + i);
            for (int i = 0; i < 3; i++)
                backPackSlots[i] = rs.getInt("back_slot" + i);


        }

        return new Player(accountId, heroId, 0, backPackSlots, itemSlots);
    }

    public boolean databaseCheckPlayer(long player_id) throws SQLException {
        String sql = "SELECT * FROM players WHERE player_id = ? ";
        PreparedStatement pstmt  = database.prepareStatement(sql);

        pstmt.setLong(1, player_id);
        ResultSet rs = pstmt.executeQuery();

        return rs.first();
    }

    public boolean databaseCheckPlayerData(long player_id, long match_id) throws SQLException {
        String sql = "SELECT * FROM players WHERE player_id = ? AND match_id = ?";
        PreparedStatement pstmt  = database.prepareStatement(sql);

        pstmt.setLong(1, player_id);
        pstmt.setLong(2, match_id);
        ResultSet rs = pstmt.executeQuery();

        return rs.first();
    }

    public boolean databaseCheckMatch(long match_id) throws SQLException {
        String sql = "SELECT * FROM matches WHERE match_id = ?";
        PreparedStatement pstmt  = database.prepareStatement(sql);

        pstmt.setLong(1, match_id);
        ResultSet rs = pstmt.executeQuery();

        return rs.first();
    }

    public void close() {
        try {
            System.out.println("Database connection closed");
            database.close();
        }
        catch (SQLException e){
            System.out.println("Problem closing connection with database");
            System.out.println(e.getMessage());
        }
    }
}
