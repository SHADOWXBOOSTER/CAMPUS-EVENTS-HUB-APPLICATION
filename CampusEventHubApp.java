import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

interface Registrable {
    boolean register(String name);
    boolean canRegister();
}

abstract class Event implements Comparable<Event>, Registrable {
    private static int totalEvents = 0;
    private final int id;
    private String name;
    private String date;
    private String venue;
    private int capacity;
    private int registered;
    
    public Event(int id, String name, String date, String venue, int capacity) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.registered = 0;
        totalEvents++;
    }
    
    public final int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getVenue() { return venue; }
    public int getCapacity() { return capacity; }
    public int getRegistered() { return registered; }
    public void setName(String name) { this.name = name; }
    public void setDate(String date) { this.date = date; }
    public void setVenue(String venue) { this.venue = venue; }
    public static void decrementTotal() { totalEvents--; }
    public static int getTotalEvents() { return totalEvents; }
    
    public boolean registerParticipant() {
        if (registered < capacity) { registered++; return true; }
        return false;
    }
    
    @Override
    public boolean register(String name) { return registerParticipant(); }
    
    @Override
    public boolean canRegister() { return registered < capacity; }
    
    @Override
    public int compareTo(Event other) { return this.name.compareToIgnoreCase(other.name); }
    
    public abstract String getCategory();
    public abstract String getDetails();
    public abstract void updateDetails(Scanner sc);
    
    public void updateDetailsGUI(String[] data) {}

    class EventStats {
        public double getOccupancy() { return (registered * 100.0) / capacity; }
    }
    
    public EventStats getStats() { return new EventStats(); }
    
    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s)\n   Date: %s | Venue: %s\n   Occupancy: %d/%d\n   %s", 
            id, name, getCategory(), date, venue, registered, capacity, getDetails());
    }
}


class Hackathon extends Event {
    private String theme;
    private int teamSize;
    private String prize;
    
    public Hackathon(int id, String name, String date, String venue, int capacity, 
                     String theme, int teamSize, String prize) {
        super(id, name, date, venue, capacity);
        this.theme = theme;
        this.teamSize = teamSize;
        this.prize = prize;
    }
    
    @Override
    public String getCategory() { return "Hackathon"; }
    @Override
    public String getDetails() { return String.format("Theme: %s | Team Size: %d | Prize: %s", theme, teamSize, prize); }
    @Override
    public void updateDetails(Scanner sc) {} 
    
    @Override
    public void updateDetailsGUI(String[] data) {
        setTheme(data[0]);
        setTeamSize(Integer.parseInt(data[1]));
        setPrize(data[2]);
    }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public int getTeamSize() { return teamSize; }
    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }
    public String getPrize() { return prize; }
    public void setPrize(String prize) { this.prize = prize; }
}

class Fest extends Event {
    private String festType;
    private int days;
    
    public Fest(int id, String name, String date, String venue, int capacity, String festType, int days) {
        super(id, name, date, venue, capacity);
        this.festType = festType;
        this.days = days;
    }
    
    @Override
    public String getCategory() { return "Fest"; }
    @Override
    public String getDetails() { return String.format("Type: %s | Duration: %d days", festType, days); }
    @Override
    public void updateDetails(Scanner sc) {}

    @Override
    public void updateDetailsGUI(String[] data) {
        setFestType(data[0]);
        setDays(Integer.parseInt(data[1]));
    }

    public String getFestType() { return festType; }
    public void setFestType(String festType) { this.festType = festType; }
    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }
}

class Seminar extends Event {
    private String speaker;
    private String topic;
    
    public Seminar(int id, String name, String date, String venue, int capacity, String speaker, String topic) {
        super(id, name, date, venue, capacity);
        this.speaker = speaker;
        this.topic = topic;
    }
    
    @Override
    public String getCategory() { return "Seminar"; }
    @Override
    public String getDetails() { return String.format("Speaker: %s | Topic: %s", speaker, topic); }
    @Override
    public void updateDetails(Scanner sc) {}

    @Override
    public void updateDetailsGUI(String[] data) {
        setSpeaker(data[0]);
        setTopic(data[1]);
    }

    public String getSpeaker() { return speaker; }
    public void setSpeaker(String speaker) { this.speaker = speaker; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}

class SportsEvent extends Event {
    private String sportType;
    
    public SportsEvent(int id, String name, String date, String venue, int capacity, String sportType) {
        super(id, name, date, venue, capacity);
        this.sportType = sportType;
    }
    
    @Override
    public String getCategory() { return "Sports"; }
    @Override
    public String getDetails() { return "Sport: " + sportType; }
    @Override
    public void updateDetails(Scanner sc) {}

    @Override
    public void updateDetailsGUI(String[] data) {
        setSportType(data[0]);
    }

    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }
}

class Committee {
    private String position;
    private String memberName;
    private String contact;
    
    public Committee(String position, String memberName, String contact) {
        this.position = position;
        this.memberName = memberName;
        this.contact = contact;
    }
    @Override
    public String toString() { return String.format("• %s: %s (%s)", position, memberName, contact); }
}

class ClubEvent extends Event {
    private String clubName;
    public ClubEvent(int id, String name, String date, String venue, int capacity, String clubName) {
        super(id, name, date, venue, capacity);
        this.clubName = clubName;
    }
    @Override
    public String getCategory() { return "Club Event"; }
    @Override
    public String getDetails() { return "Organized by: " + clubName; }
    @Override
    public void updateDetails(Scanner sc) {}
}

class Club {
    private static int clubCounter = 0;
    private int id;
    private String name;
    private String description;
    private ArrayList<Committee> committee;
    private LinkedList<ClubEvent> events;
    
    public Club(String name, String description) {
        this.id = ++clubCounter;
        this.name = name;
        this.description = description;
        this.committee = new ArrayList<>();
        this.events = new LinkedList<>();
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public void addMember(Committee m) { committee.add(m); }
    public void addEvent(ClubEvent e) { events.addFirst(e); }
    public ArrayList<Committee> getCommittee() { return committee; }
    public LinkedList<ClubEvent> getEvents() { return events; }
    
    @Override
    public String toString() {
        return String.format("ID: %d | %s\n   About: %s", id, name, description);
    }
}


class EventManager {
    private ArrayList<Event> allEvents;
    private Vector<Hackathon> hackathons;
    private Vector<Fest> fests;
    private Vector<Seminar> seminars;
    private Vector<SportsEvent> sports;
    private LinkedList<Club> clubs;
    
    public EventManager() {
        allEvents = new ArrayList<>();
        hackathons = new Vector<>();
        fests = new Vector<>();
        seminars = new Vector<>();
        sports = new Vector<>();
        clubs = new LinkedList<>();
    }
    
    public void add(Event e) {
        allEvents.add(e);
        if (e instanceof Hackathon) hackathons.add((Hackathon)e);
        else if (e instanceof Fest) fests.add((Fest)e);
        else if (e instanceof Seminar) seminars.add((Seminar)e);
        else if (e instanceof SportsEvent) sports.add((SportsEvent)e);
    }
    
    public boolean delete(int id) {
        Event e = find(id);
        if (e != null) {
            allEvents.remove(e);
            if (e instanceof Hackathon) hackathons.remove(e);
            else if (e instanceof Fest) fests.remove(e);
            else if (e instanceof Seminar) seminars.remove(e);
            else if (e instanceof SportsEvent) sports.remove(e);
            Event.decrementTotal();
            return true;
        }
        return false;
    }
    
    public Event find(int id) {
        for (Event e : allEvents) if (e.getId() == id) return e;
        return null;
    }
    
    public Club findClub(int id) {
        for (Club c : clubs) if (c.getId() == id) return c;
        return null;
    }
    
    public void addClub(Club c) { clubs.add(c); }
    
    public Vector<Hackathon> getHackathons() { return hackathons; }
    public Vector<Fest> getFests() { return fests; }
    public Vector<Seminar> getSeminars() { return seminars; }
    public Vector<SportsEvent> getSports() { return sports; }
    public LinkedList<Club> getClubs() { return clubs; }
    public ArrayList<Event> getAll() { return allEvents; }
    
    public String getStats() {
        int total = Event.getTotalEvents();
        int reg = 0, cap = 0;
        for (Event e : allEvents) { reg += e.getRegistered(); cap += e.getCapacity(); }
        double occ = cap > 0 ? (reg * 100.0 / cap) : 0;
        return String.format("Total Events: %d\nTotal Registrations: %d\nTotal Capacity: %d\nOverall Occupancy: %.1f%%", 
            total, reg, cap, occ);
    }
}


public class CampusEventHubApp extends JFrame {
    private static EventManager mgr = new EventManager();
    private static int nextId = 1;
    
    private JTextArea displayArea;
    
    public CampusEventHubApp() {
        setTitle("Campus Events Hub - Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 245));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(92, 133, 214));
        headerPanel.setPreferredSize(new Dimension(1200, 100));
        headerPanel.setLayout(new GridBagLayout());
        
        JLabel headerLabel = new JLabel("🎓 Campus Events Hub", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(new Color(255, 0, 0)); 
        
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        centerContainer.setBackground(new Color(240, 240, 245));
        
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(15, 15, 15, 15));
        displayArea.setText("Welcome to Campus Events Hub!\nClick a button on the right to get started.");
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        centerContainer.add(scrollPane, BorderLayout.CENTER);
        
        add(centerContainer, BorderLayout.CENTER);
        
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(8, 1, 10, 15));
        navPanel.setBackground(new Color(240, 240, 245));
        navPanel.setBorder(new EmptyBorder(20, 0, 20, 20));
        navPanel.setPreferredSize(new Dimension(280, 0));

        navPanel.add(createTileButton("📚", "Hackathons", e -> manageHackathons()));
        navPanel.add(createTileButton("🎉", "Fests", e -> manageFests()));
        navPanel.add(createTileButton("🎤", "Seminars", e -> manageSeminars()));
        navPanel.add(createTileButton("⚽", "Sports", e -> manageSports()));
        navPanel.add(createTileButton("🏛️", "Clubs", e -> manageClubs()));
        navPanel.add(createTileButton("📋", "View All", e -> viewAllEvents()));
        navPanel.add(createTileButton("📊", "Stats", e -> showStatistics()));
        navPanel.add(createTileButton("❌", "Exit", e -> System.exit(0)));
        
        add(navPanel, BorderLayout.EAST);
        
        loadData();
    }
 
    private JButton createTileButton(String icon, String text, ActionListener action) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setPreferredSize(new Dimension(60, 0));
        
        JLabel textLabel = new JLabel(text, SwingConstants.LEFT);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        textLabel.setForeground(Color.BLACK); 
        
        btn.add(iconLabel, BorderLayout.WEST);
        btn.add(textLabel, BorderLayout.CENTER);

        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(255, 223, 0), 1, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 223, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });
        
        return btn;
    }


    private void manageHackathons() {
        String[] options = {"Add Hackathon", "View Hackathons", "Update Hackathon", "Delete Hackathon", "Register"};
        int choice = JOptionPane.showOptionDialog(this, "Hackathon Management", "Hackathons",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if(choice == 0) addHackathon();
        else if(choice == 1) viewEvents(mgr.getHackathons(), "HACKATHONS");
        else if(choice == 2) updateHackathon();
        else if(choice == 3) deleteEvent("Hackathon");
        else if(choice == 4) registerForEvent();
    }

    private void manageFests() {
        String[] options = {"Add Fest", "View Fests", "Update Fest", "Delete Fest", "Register"};
        int choice = JOptionPane.showOptionDialog(this, "Fest Management", "Fests",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if(choice == 0) addFest();
        else if(choice == 1) viewEvents(mgr.getFests(), "FESTS");
        else if(choice == 2) updateFest();
        else if(choice == 3) deleteEvent("Fest");
        else if(choice == 4) registerForEvent();
    }

    private void manageSeminars() {
        String[] options = {"Add Seminar", "View Seminars", "Update Seminar", "Delete Seminar", "Register"};
        int choice = JOptionPane.showOptionDialog(this, "Seminar Management", "Seminars",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if(choice == 0) addSeminar();
        else if(choice == 1) viewEvents(mgr.getSeminars(), "SEMINARS");
        else if(choice == 2) updateSeminar();
        else if(choice == 3) deleteEvent("Seminar");
        else if(choice == 4) registerForEvent();
    }

    private void manageSports() {
        String[] options = {"Add Sport", "View Sports", "Update Sport", "Delete Sport", "Register"};
        int choice = JOptionPane.showOptionDialog(this, "Sports Management", "Sports",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if(choice == 0) addSports();
        else if(choice == 1) viewEvents(mgr.getSports(), "SPORTS");
        else if(choice == 2) updateSports();
        else if(choice == 3) deleteEvent("Sports");
        else if(choice == 4) registerForEvent();
    }

    private void manageClubs() {
        String[] options = {"Add Club", "View Clubs", "Add Member", "Add Event"};
        int choice = JOptionPane.showOptionDialog(this, "Club Management", "Clubs",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        
        if(choice == 0) addClub();
        else if(choice == 1) viewClubs();
        else if(choice == 2) addCommittee();
        else if(choice == 3) addClubEvent();
    }


    private void addHackathon() {
        JTextField name = new JTextField();
        JTextField date = new JTextField();
        JTextField venue = new JTextField();
        JTextField cap = new JTextField();
        JTextField theme = new JTextField();
        JTextField team = new JTextField();
        JTextField prize = new JTextField();
        
        Object[] message = {
            "Name:", name, "Date:", date, "Venue:", venue, "Capacity:", cap,
            "Theme:", theme, "Team Size:", team, "Prize:", prize
        };

        if (JOptionPane.showConfirmDialog(this, message, "Add Hackathon", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                mgr.add(new Hackathon(nextId++, name.getText(), date.getText(), venue.getText(),
                    Integer.parseInt(cap.getText()), theme.getText(), Integer.parseInt(team.getText()), prize.getText()));
                displayArea.setText("✓ Hackathon Added!");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Input!"); }
        }
    }

    private void addFest() {
        JTextField name = new JTextField(); JTextField date = new JTextField(); JTextField venue = new JTextField();
        JTextField cap = new JTextField(); JTextField type = new JTextField(); JTextField days = new JTextField();
        Object[] msg = {"Name:", name, "Date:", date, "Venue:", venue, "Capacity:", cap, "Type:", type, "Days:", days};
        if (JOptionPane.showConfirmDialog(this, msg, "Add Fest", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                mgr.add(new Fest(nextId++, name.getText(), date.getText(), venue.getText(),
                    Integer.parseInt(cap.getText()), type.getText(), Integer.parseInt(days.getText())));
                displayArea.setText("✓ Fest Added!");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Input!"); }
        }
    }

    private void addSeminar() {
        JTextField name = new JTextField(); JTextField date = new JTextField(); JTextField venue = new JTextField();
        JTextField cap = new JTextField(); JTextField spk = new JTextField(); JTextField topic = new JTextField();
        Object[] msg = {"Name:", name, "Date:", date, "Venue:", venue, "Capacity:", cap, "Speaker:", spk, "Topic:", topic};
        if (JOptionPane.showConfirmDialog(this, msg, "Add Seminar", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                mgr.add(new Seminar(nextId++, name.getText(), date.getText(), venue.getText(),
                    Integer.parseInt(cap.getText()), spk.getText(), topic.getText()));
                displayArea.setText("✓ Seminar Added!");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Input!"); }
        }
    }

    private void addSports() {
        JTextField name = new JTextField(); JTextField date = new JTextField(); JTextField venue = new JTextField();
        JTextField cap = new JTextField(); JTextField type = new JTextField();
        Object[] msg = {"Name:", name, "Date:", date, "Venue:", venue, "Capacity:", cap, "Sport Type:", type};
        if (JOptionPane.showConfirmDialog(this, msg, "Add Sports", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                mgr.add(new SportsEvent(nextId++, name.getText(), date.getText(), venue.getText(),
                    Integer.parseInt(cap.getText()), type.getText()));
                displayArea.setText("✓ Sports Event Added!");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Invalid Input!"); }
        }
    }

    private void addClub() {
        JTextField name = new JTextField(); JTextField desc = new JTextField();
        if (JOptionPane.showConfirmDialog(this, new Object[]{"Name:", name, "Description:", desc}, "Add Club", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            mgr.addClub(new Club(name.getText(), desc.getText()));
            displayArea.setText("✓ Club Added!");
        }
    }

    private void addCommittee() {
        String idStr = JOptionPane.showInputDialog("Enter Club ID:");
        if (idStr != null) {
            Club c = mgr.findClub(Integer.parseInt(idStr));
            if (c != null) {
                JTextField pos = new JTextField(); JTextField mem = new JTextField(); JTextField con = new JTextField();
                if (JOptionPane.showConfirmDialog(this, new Object[]{"Position:", pos, "Name:", mem, "Contact:", con}, "Add Member", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    c.addMember(new Committee(pos.getText(), mem.getText(), con.getText()));
                    displayArea.setText("✓ Member Added to " + c.getName());
                }
            } else JOptionPane.showMessageDialog(this, "Club not found!");
        }
    }

    private void addClubEvent() {
        String idStr = JOptionPane.showInputDialog("Enter Club ID:");
        if (idStr != null) {
            Club c = mgr.findClub(Integer.parseInt(idStr));
            if (c != null) {
                JTextField name = new JTextField(); JTextField date = new JTextField(); JTextField venue = new JTextField(); JTextField cap = new JTextField();
                if (JOptionPane.showConfirmDialog(this, new Object[]{"Name:", name, "Date:", date, "Venue:", venue, "Capacity:", cap}, "Add Club Event", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    ClubEvent ce = new ClubEvent(nextId++, name.getText(), date.getText(), venue.getText(), Integer.parseInt(cap.getText()), c.getName());
                    c.addEvent(ce);
                    mgr.add(ce);
                    displayArea.setText("✓ Club Event Added!");
                }
            } else JOptionPane.showMessageDialog(this, "Club not found!");
        }
    }


    private <T extends Event> void viewEvents(Vector<T> events, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("════════ ").append(title).append(" ════════\n\n");
        if (events.isEmpty()) sb.append("No events found.");
        else for (T e : events) sb.append(e.toString()).append("\n\n");
        displayArea.setText(sb.toString());
    }

    private void viewClubs() {
        StringBuilder sb = new StringBuilder("════════ CLUBS ════════\n\n");
        if (mgr.getClubs().isEmpty()) sb.append("No clubs found.");
        else {
            for (Club c : mgr.getClubs()) {
                sb.append(c.toString()).append("\n");
                if (!c.getCommittee().isEmpty()) {
                    sb.append("  Committee:\n");
                    for (Committee m : c.getCommittee()) sb.append("    ").append(m).append("\n");
                }
                if (!c.getEvents().isEmpty()) {
                    sb.append("  Events:\n");
                    for (ClubEvent e : c.getEvents()) sb.append("    - ").append(e.getName()).append("\n");
                }
                sb.append("\n");
            }
        }
        displayArea.setText(sb.toString());
    }

    private void viewAllEvents() {
        StringBuilder sb = new StringBuilder("════════ ALL CAMPUS EVENTS ════════\n\n");
        for (Event e : mgr.getAll()) sb.append(e.toString()).append("\n\n");
        displayArea.setText(sb.toString());
    }

    private void showStatistics() {
        displayArea.setText("📊 STATISTICS\n\n" + mgr.getStats());
    }

    private void updateHackathon() {
        String id = JOptionPane.showInputDialog("Enter Hackathon ID to update:");
        if (id != null) {
            Event e = mgr.find(Integer.parseInt(id));
            if (e instanceof Hackathon) {
                Hackathon h = (Hackathon)e;
                JTextField th = new JTextField(h.getTheme()); JTextField tm = new JTextField(""+h.getTeamSize()); JTextField pr = new JTextField(h.getPrize());
                if (JOptionPane.showConfirmDialog(this, new Object[]{"New Theme:", th, "New Team Size:", tm, "New Prize:", pr}, "Update", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    h.updateDetailsGUI(new String[]{th.getText(), tm.getText(), pr.getText()});
                    displayArea.setText("✓ Updated!");
                }
            } else JOptionPane.showMessageDialog(this, "ID not found or not a Hackathon.");
        }
    }

    private void updateFest() {
        String id = JOptionPane.showInputDialog("Enter Fest ID to update:");
        if (id != null) {
            Event e = mgr.find(Integer.parseInt(id));
            if (e instanceof Fest) {
                Fest f = (Fest)e;
                JTextField tp = new JTextField(f.getFestType()); JTextField d = new JTextField(""+f.getDays());
                if (JOptionPane.showConfirmDialog(this, new Object[]{"New Type:", tp, "New Days:", d}, "Update", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    f.updateDetailsGUI(new String[]{tp.getText(), d.getText()});
                    displayArea.setText("✓ Updated!");
                }
            } else JOptionPane.showMessageDialog(this, "ID not found.");
        }
    }

    private void updateSeminar() {
        String id = JOptionPane.showInputDialog("Enter Seminar ID to update:");
        if (id != null) {
            Event e = mgr.find(Integer.parseInt(id));
            if (e instanceof Seminar) {
                Seminar s = (Seminar)e;
                JTextField sp = new JTextField(s.getSpeaker()); JTextField tp = new JTextField(s.getTopic());
                if (JOptionPane.showConfirmDialog(this, new Object[]{"New Speaker:", sp, "New Topic:", tp}, "Update", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    s.updateDetailsGUI(new String[]{sp.getText(), tp.getText()});
                    displayArea.setText("✓ Updated!");
                }
            } else JOptionPane.showMessageDialog(this, "ID not found.");
        }
    }

    private void updateSports() {
        String id = JOptionPane.showInputDialog("Enter Sports ID to update:");
        if (id != null) {
            Event e = mgr.find(Integer.parseInt(id));
            if (e instanceof SportsEvent) {
                JTextField tp = new JTextField(((SportsEvent)e).getSportType());
                if (JOptionPane.showConfirmDialog(this, new Object[]{"New Sport Type:", tp}, "Update", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    ((SportsEvent)e).updateDetailsGUI(new String[]{tp.getText()});
                    displayArea.setText("✓ Updated!");
                }
            } else JOptionPane.showMessageDialog(this, "ID not found.");
        }
    }

    private void deleteEvent(String type) {
        String id = JOptionPane.showInputDialog("Enter " + type + " ID to delete:");
        if (id != null) {
            if (mgr.delete(Integer.parseInt(id))) displayArea.setText("✓ Deleted " + type);
            else JOptionPane.showMessageDialog(this, "ID not found.");
        }
    }

    private void registerForEvent() {
        String id = JOptionPane.showInputDialog("Enter Event ID:");
        if (id != null) {
            Event e = mgr.find(Integer.parseInt(id));
            if (e != null) {
                String name = JOptionPane.showInputDialog("Enter Participant Name:");
                if (name != null && e.register(name)) displayArea.setText("✓ Registered for " + e.getName());
                else displayArea.setText("✗ Registration Failed (Full or Invalid)");
            } else JOptionPane.showMessageDialog(this, "Event not found.");
        }
    }

    private void loadData() {
        mgr.add(new Hackathon(nextId++, "AI Challenge", "2025-12-15", "Lab 333", 150, "GEN AI", 5, "$1000"));
        mgr.add(new Fest(nextId++, "BITS Tech Fest", "2025-11-11", "Auditorium", 5000, "Tech", 3));
        mgr.add(new Seminar(nextId++, "Cloud Computing and IOT - New Future", "2025-10-23", "Media Room", 500, "Dr. Naradasu Likith", "Cloud Computing and IOT"));
        mgr.add(new SportsEvent(nextId++, "RECHARGE", "2026-03-18", "Ground", 20000, "Multi-Sport"));
        Club c1 = new Club("Google", "Google Developer Student Clubs (GDSC) is a community group for students interested in Google developer technologies.");
        c1.addMember(new Committee("President", "Tanush Reddy Lingam", "Tanush.lingam@gmail.com"));
        mgr.addClub(c1);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new CampusEventHubApp().setVisible(true));
    }
}
