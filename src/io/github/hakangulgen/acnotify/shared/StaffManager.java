package io.github.hakangulgen.acnotify.shared;

import java.util.Collection;
import java.util.HashSet;

public class StaffManager {

    private final Collection<String> staff = new HashSet<>();

    public void addStaff(String name) { staff.add(name); }

    public void removeStaff(String name) { staff.remove(name); }

    public Collection<String> getAllStaff() { return staff; }

}
