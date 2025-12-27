package com.dwarfeng.dutil.basic.gui;

import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel.ColumnValueSettingPolicy;
import com.dwarfeng.dutil.basic.gui.swing.MappingTableModel.TableColumn;
import org.junit.*;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.*;

import static org.junit.Assert.*;

public class MappingTableModelSubListTest {

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
    private static List<Employee> subList;

    private static final Employee EMPLOYEE_A = new Employee(1, "A", 100.0);
    private static final Employee EMPLOYEE_B = new Employee(2, "B", 200.0);
    private static final Employee EMPLOYEE_C = new Employee(3, "C", 300.0);
    private static final Employee EMPLOYEE_D = new Employee(4, "D", 300.0);
    private static final Employee EMPLOYEE_E = new Employee(5, "E", 300.0);
    private static final Employee EMPLOYEE_F = new Employee(6, "F", 300.0);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(EMPLOYEE_A);
        employees.add(EMPLOYEE_B);
        employees.add(EMPLOYEE_C);
        employees.add(EMPLOYEE_D);
        employees.add(EMPLOYEE_E);
        tableModel = new MappingTableModel<>(Employee.class, Employee.class, employees);
        tableModelListener = new InterceptorTableModelListener();
        tableModel.addTableModelListener(tableModelListener);
        subList = tableModel.subList(1, 4);
    }

    @After
    public void tearDown() throws Exception {
        tableModel = null;
        tableModelListener = null;
        subList.clear();
    }

    @Test
    public final void testHashCode() {
        List<Employee> list = new ArrayList<>(Arrays.asList(EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D));
        assertEquals(list.hashCode(), subList.hashCode());
    }

    @Test
    public final void testEquals() {
        List<Employee> list = new ArrayList<>(Arrays.asList(EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D));
        assertEquals(list, subList);
    }

    @Test
    public final void testSize() {
        assertEquals(3, subList.size());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public final void testIsEmpty() {
        assertFalse(subList.isEmpty());
        subList.clear();
        assertTrue(subList.isEmpty());
    }

    @Test
    public final void testContains() {
        assertFalse(subList.contains(EMPLOYEE_A));
        assertTrue(subList.contains(EMPLOYEE_B));
        assertTrue(subList.contains(EMPLOYEE_C));
        assertTrue(subList.contains(EMPLOYEE_D));
        assertFalse(subList.contains(EMPLOYEE_E));
    }

    @Test
    public final void testIterator() {
        Iterator<Employee> it = subList.iterator();
        assertTrue(it.hasNext());
        Employee next = it.next();
        assertEquals(EMPLOYEE_B, next);
        it.remove();
        assertEquals(2, subList.size());
        assertEquals(4, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(1, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(1, tableModelListener.eventList.get(0).getLastRow());
        next = it.next();
        assertEquals(EMPLOYEE_C, next);
        next = it.next();
        assertEquals(EMPLOYEE_D, next);
    }

    @Test
    public final void testToArray() {
        assertArrayEquals(new Employee[]{EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D}, subList.toArray());
    }

    @Test
    public final void testToArrayTArray() {
        assertArrayEquals(new Employee[]{EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D}, subList.toArray(new Employee[0]));
    }

    @Test
    public final void testAddE() {
        assertTrue(subList.add(EMPLOYEE_F));
        assertEquals(4, subList.size());
        assertEquals(6, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(4, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(4, tableModelListener.eventList.get(0).getLastRow());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_F, EMPLOYEE_E},
                tableModel.toArray());
    }

    @Test
    public final void testRemoveObject() {
        assertFalse(subList.remove(EMPLOYEE_F));
        assertTrue(subList.remove(EMPLOYEE_C));
        assertEquals(2, subList.size());
        assertEquals(4, tableModel.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_D, EMPLOYEE_E}, tableModel.toArray());
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public final void testContainsAll() {
        assertTrue(subList.containsAll(Arrays.asList(EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D)));
        assertTrue(subList.containsAll(Arrays.asList(EMPLOYEE_B, EMPLOYEE_B, EMPLOYEE_B)));
        assertTrue(subList.containsAll(Arrays.asList(EMPLOYEE_C, EMPLOYEE_D)));
        assertTrue(subList.containsAll(Arrays.asList(EMPLOYEE_D)));
        assertFalse(subList.containsAll(Arrays.asList(EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_E)));
    }

    @Test
    public final void testAddAllCollectionOf() {
        assertTrue(subList.addAll(Arrays.asList(EMPLOYEE_E, EMPLOYEE_F)));
        assertArrayEquals(
                new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_E, EMPLOYEE_F, EMPLOYEE_E},
                tableModel.toArray());
    }

    @Test
    public final void testAddAllIntCollectionOf() {
        assertTrue(subList.addAll(1, Arrays.asList(EMPLOYEE_E, EMPLOYEE_F)));
        assertArrayEquals(
                new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_E, EMPLOYEE_F, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_E},
                tableModel.toArray());
    }

    @Test
    public final void testRemoveAll() {
        assertFalse(subList.removeAll(Arrays.asList(EMPLOYEE_A, EMPLOYEE_E, EMPLOYEE_F)));
        assertTrue(subList.removeAll(Arrays.asList(EMPLOYEE_C, EMPLOYEE_D)));
        assertEquals(1, subList.size());
        assertEquals(3, tableModel.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public final void testRetainAll() {
        assertFalse(subList.retainAll(Arrays.asList(EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D)));
        assertTrue(subList.retainAll(Arrays.asList(EMPLOYEE_B)));
        assertEquals(1, subList.size());
        assertEquals(3, tableModel.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public final void testClear() {
        subList.clear();
        assertTrue(subList.isEmpty());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_E}, tableModel.toArray());
    }

    @Test
    public final void testGet() {
        assertEquals(EMPLOYEE_B, subList.get(0));
        assertEquals(EMPLOYEE_C, subList.get(1));
        assertEquals(EMPLOYEE_D, subList.get(2));
    }

    @Test
    public final void testSet() {
        assertEquals(EMPLOYEE_C, subList.set(1, EMPLOYEE_F));
        assertEquals(3, subList.size());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.UPDATE, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_F, EMPLOYEE_D, EMPLOYEE_E},
                tableModel.toArray());
    }

    @Test
    public final void testAdd() {
        subList.add(EMPLOYEE_F);
        assertArrayEquals(new Employee[]{EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_F}, subList.toArray());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_F, EMPLOYEE_E},
                tableModel.toArray());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(4, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(4, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testAddIntE() {
        subList.add(1, EMPLOYEE_F);
        assertArrayEquals(new Employee[]{EMPLOYEE_B, EMPLOYEE_F, EMPLOYEE_C, EMPLOYEE_D}, subList.toArray());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_F, EMPLOYEE_C, EMPLOYEE_D, EMPLOYEE_E},
                tableModel.toArray());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.INSERT, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testRemoveInt() {
        subList.remove(1);
        assertArrayEquals(new Employee[]{EMPLOYEE_B, EMPLOYEE_D}, subList.toArray());
        assertArrayEquals(new Employee[]{EMPLOYEE_A, EMPLOYEE_B, EMPLOYEE_D, EMPLOYEE_E}, tableModel.toArray());
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testIndexOf() {
        assertEquals(-1, subList.indexOf(EMPLOYEE_A));
        assertEquals(0, subList.indexOf(EMPLOYEE_B));
        assertEquals(1, subList.indexOf(EMPLOYEE_C));
        assertEquals(2, subList.indexOf(EMPLOYEE_D));
        assertEquals(-1, subList.indexOf(EMPLOYEE_E));
    }

    @Test
    public final void testLastIndexOf() {
        assertEquals(-1, subList.lastIndexOf(EMPLOYEE_A));
        assertEquals(0, subList.lastIndexOf(EMPLOYEE_B));
        assertEquals(1, subList.lastIndexOf(EMPLOYEE_C));
        assertEquals(2, subList.lastIndexOf(EMPLOYEE_D));
        assertEquals(-1, subList.lastIndexOf(EMPLOYEE_E));
    }

    @Test
    public final void testListIterator() {
        ListIterator<Employee> it = subList.listIterator();
        assertTrue(it.hasNext());
        assertFalse(it.hasPrevious());
        Employee employee = it.next();
        assertEquals(EMPLOYEE_B, employee);
        it.remove();
        assertEquals(2, subList.size());
        assertEquals(4, tableModel.size());
        employee = it.next();
        assertEquals(EMPLOYEE_C, employee);
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(1, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(1, tableModelListener.eventList.get(0).getLastRow());
    }

    @Test
    public final void testListIteratorInt() {
        ListIterator<Employee> it = subList.listIterator(1);
        assertTrue(it.hasNext());
        assertTrue(it.hasPrevious());
        Employee employee = it.next();
        assertEquals(EMPLOYEE_C, employee);
        it.remove();
        assertEquals(2, subList.size());
        assertEquals(4, tableModel.size());
        employee = it.next();
        assertEquals(EMPLOYEE_D, employee);
        assertEquals(1, tableModelListener.eventList.size());
        assertEquals(TableModelEvent.DELETE, tableModelListener.eventList.get(0).getType());
        assertEquals(2, tableModelListener.eventList.get(0).getFirstRow());
        assertEquals(2, tableModelListener.eventList.get(0).getLastRow());
    }
}
