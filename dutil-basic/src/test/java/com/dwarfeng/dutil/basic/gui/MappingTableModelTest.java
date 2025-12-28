package com.dwarfeng.dutil.basic.gui;

import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel.ColumnValueSettingPolicy;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel.TableColumn;
import org.junit.*;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.*;

import static org.junit.Assert.*;

public class MappingTableModelTest {

    @TableColumn(columnValueGetterName = "getId", columnName = "id", columnClass = int.class)
    @TableColumn(
            columnValueGetterName = "getName", columnName = "name", columnClass = String.class, editable = true,
            columnValueSettingPolicy = ColumnValueSettingPolicy.NOTIFICATION_ONLY
    )
    @TableColumn(
            columnValueGetterName = "getSalary", columnName = "salary", columnClass = double.class, editable = true,
            columnValueSettingPolicy = ColumnValueSettingPolicy.CALL_SETTER, columnValueSetterName = "setSalary"
    )
    public final static class Employee implements MappingTableModel.MappingInfos {

        private int id;
        private String name;
        private double salary;

        private Employee(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

    }

    private static final class InterceptorTableModelListener implements TableModelListener {

        private final List<TableModelEvent> eventList = new ArrayList<>();

        @Override
        public void tableChanged(TableModelEvent e) {
            eventList.add(e);
        }

    }

    private static MappingTableModel<Employee> tableModel;
    private static InterceptorTableModelListener tableModelListener;

    private static final Employee EMPLOYEE_DWARFENG = new Employee(1, "DwArFeng", 100.0);
    private static final Employee EMPLOYEE_RAE = new Employee(2, "Rae", 200.0);
    private static final Employee EMPLOYEE_BOSS = new Employee(3, "Boss", 300.0);
    private static final Employee EMPLOYEE_NEWBIE = new Employee(4, "Newbie", 300.0);

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        List<Employee> employees = new ArrayList<>();
        employees.add(EMPLOYEE_DWARFENG);
        employees.add(EMPLOYEE_RAE);
        employees.add(EMPLOYEE_BOSS);
        tableModel = new MappingTableModel<>(Employee.class, Employee.class, employees);
        tableModelListener = new InterceptorTableModelListener();
        tableModel.addTableModelListener(tableModelListener);
    }

    @After
    public void tearDown() {
        tableModel = null;
        tableModelListener = null;
    }

    @Test
    public final void testGetRowCount() {
        assertEquals(3, tableModel.getRowCount());
    }

    @Test
    public final void testGetColumnCount() {
        assertEquals(3, tableModel.getColumnCount());
    }

    @Test
    public final void testGetColumnNameInt() {
        assertEquals("id", tableModel.getColumnName(0));
        assertEquals("name", tableModel.getColumnName(1));
        assertEquals("salary", tableModel.getColumnName(2));
    }

    @Test
    public final void testGetColumnClassInt() {
        assertEquals(int.class, tableModel.getColumnClass(0));
        assertEquals(String.class, tableModel.getColumnClass(1));
        assertEquals(double.class, tableModel.getColumnClass(2));
    }

    @Test
    public final void testIsCellEditable() {
        assertFalse(tableModel.isCellEditable(0, 0));
        assertTrue(tableModel.isCellEditable(0, 1));
        assertTrue(tableModel.isCellEditable(0, 2));
    }

    @Test
    public final void testGetValueAt() {
        assertEquals(1, tableModel.getValueAt(0, 0));
        assertEquals("DwArFeng", tableModel.getValueAt(0, 1));
        assertEquals(100.0, (double) tableModel.getValueAt(0, 2), 0.0001);
        tableModel.add(null);
        assertNull(tableModel.getValueAt(3, 0));
        assertNull(tableModel.getValueAt(3, 1));
        assertNull(tableModel.getValueAt(3, 2));
    }

    @Test
    public final void testSetValueAtObjectIntInt() {
        tableModel.setValueAt("DWA", 0, 1);
        assertEquals(1, tableModelListener.eventList.size());
        TableModelEvent event = tableModelListener.eventList.get(0);
        assertEquals(0, event.getFirstRow());
        assertEquals(0, event.getLastRow());
        assertEquals(1, event.getColumn());
        assertEquals(TableModelEvent.UPDATE, event.getType());
    }

    @Test
    public final void testSize() {
        assertEquals(3, tableModel.size());
    }

    @Test
    public final void testIsEmpty() {
        assertFalse(tableModel.isEmpty());
    }

    @Test
    public final void testContains() {
        assertTrue(tableModel.contains(EMPLOYEE_DWARFENG));
        assertTrue(tableModel.contains(EMPLOYEE_RAE));
        assertTrue(tableModel.contains(EMPLOYEE_BOSS));
        assertFalse(tableModel.contains(new Employee(123, "NAN", 12.450)));
    }

    @Test
    public final void testIterator() {
        Iterator<Employee> it = tableModel.iterator();
        Employee next;

        assertTrue(it.hasNext());
        next = it.next();
        assertEquals(next, EMPLOYEE_DWARFENG);
        it.remove();
        assertEquals(2, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(0, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(0, tableModelListener.eventList.get(0).getLastRow());

        next = it.next();
        assertEquals(next, EMPLOYEE_RAE);
        next = it.next();
        assertEquals(next, EMPLOYEE_BOSS);
    }

    @Test
    public final void testToArray() {
        Object[] array = tableModel.toArray();
        assertArrayEquals(new Object[]{EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_BOSS}, array);
    }

    @Test
    public final void testToArrayTArray() {
        Employee[] array = tableModel.toArray(new Employee[0]);
        assertArrayEquals(new Object[]{EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_BOSS}, array);
    }

    @Test
    public final void testAddE() {
        assertTrue(tableModel.add(EMPLOYEE_DWARFENG));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(3, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(3, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testRemoveObject() {
        assertTrue(tableModel.remove(EMPLOYEE_BOSS));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testContainsAll() {
        assertTrue(tableModel.containsAll(Arrays.asList(EMPLOYEE_BOSS, EMPLOYEE_DWARFENG, EMPLOYEE_RAE)));
    }

    @Test
    public final void testAddAllCollectionOf() {
        assertTrue(tableModel.addAll(Arrays.asList(EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_BOSS)));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(3, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(5, tableModelListener.eventList.get(0).getLastRow());
        assertEquals(6, tableModel.size());
        assertArrayEquals(new Employee[]{EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_BOSS, EMPLOYEE_DWARFENG,
                EMPLOYEE_RAE, EMPLOYEE_BOSS}, tableModel.toArray());
    }

    @Test
    public final void testAddAllIntCollectionOf() {
        assertTrue(tableModel.addAll(2, Arrays.asList(EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_BOSS)));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(4, tableModelListener.eventList.get(0).getLastRow());
        assertEquals(6, tableModel.size());
        assertArrayEquals(new Employee[]{EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_DWARFENG, EMPLOYEE_RAE,
                EMPLOYEE_BOSS, EMPLOYEE_BOSS}, tableModel.toArray());
    }

    @Test
    public final void testRemoveAll() {
        assertTrue(tableModel.removeAll(Arrays.asList(EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_NEWBIE)));
        assertEquals(1, tableModel.size());
        assertTrue(tableModel.contains(EMPLOYEE_BOSS));
    }

    @Test
    public final void testRetainAll() {
        assertTrue(tableModel.retainAll(Arrays.asList(EMPLOYEE_DWARFENG, EMPLOYEE_RAE, EMPLOYEE_NEWBIE)));
        assertEquals(2, tableModel.size());
        assertTrue(tableModel.contains(EMPLOYEE_DWARFENG));
        assertTrue(tableModel.contains(EMPLOYEE_RAE));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public final void testClear() {
        tableModel.clear();
        assertTrue(tableModel.isEmpty());
        assertEquals(0, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(0, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testGet() {
        assertEquals(EMPLOYEE_DWARFENG, tableModel.get(0));
        assertEquals(EMPLOYEE_RAE, tableModel.get(1));
        assertEquals(EMPLOYEE_BOSS, tableModel.get(2));
    }

    @Test
    public final void testSet() {
        assertEquals(EMPLOYEE_DWARFENG, tableModel.set(0, EMPLOYEE_NEWBIE));
        assertEquals(EMPLOYEE_NEWBIE, tableModel.get(0));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.UPDATE, tableModelListener.eventList.get(0).getType());
        assertEquals(0, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(0, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testAddIntE() {
        assertTrue(tableModel.add(EMPLOYEE_NEWBIE));
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(3, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(3, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testRemoveInt() {
        assertEquals(EMPLOYEE_RAE, tableModel.remove(1));
        assertEquals(2, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(1, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(1, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testIndexOf() {
        assertEquals(0, tableModel.indexOf(EMPLOYEE_DWARFENG));
        assertEquals(1, tableModel.indexOf(EMPLOYEE_RAE));
        assertEquals(2, tableModel.indexOf(EMPLOYEE_BOSS));
    }

    @Test
    public final void testLastIndexOf() {
        assertEquals(0, tableModel.lastIndexOf(EMPLOYEE_DWARFENG));
        assertEquals(1, tableModel.lastIndexOf(EMPLOYEE_RAE));
        assertEquals(2, tableModel.lastIndexOf(EMPLOYEE_BOSS));
    }

    @Test
    public final void testListIterator() {
        ListIterator<Employee> it = tableModel.listIterator();
        assertTrue(it.hasNext());
        assertFalse(it.hasPrevious());
        Employee employee = it.next();
        assertEquals(EMPLOYEE_DWARFENG, employee);
        it.remove();
        assertEquals(2, tableModel.size());
        employee = it.next();
        assertEquals(EMPLOYEE_RAE, employee);
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(0, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(0, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testListIteratorInt() {
        ListIterator<Employee> it = tableModel.listIterator(1);
        assertTrue(it.hasNext());
        assertTrue(it.hasPrevious());
        Employee employee = it.next();
        assertEquals(EMPLOYEE_RAE, employee);
        it.remove();
        assertEquals(2, tableModel.size());
        employee = it.next();
        assertEquals(EMPLOYEE_BOSS, employee);
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(1, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(1, tableModelListener.eventList.get(0).getLastRow());
    }
}
