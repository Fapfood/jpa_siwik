package fapfood.trzebinski_jpa_practice.ui.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import fapfood.trzebinski_jpa_practice.model.Customer;
import fapfood.trzebinski_jpa_practice.model.Supplier;
import fapfood.trzebinski_jpa_practice.repository.CustomerRepository;
import fapfood.trzebinski_jpa_practice.repository.SupplierRepository;
import fapfood.trzebinski_jpa_practice.ui.editor.CustomerEditor;
import fapfood.trzebinski_jpa_practice.ui.editor.SupplierEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI(path = "/supplier")
public class SupplierUI extends UI {

    private final SupplierRepository repository;

    private final SupplierEditor editor;

    final Grid<Supplier> grid;

    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public SupplierUI(SupplierRepository repository, SupplierEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Supplier.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New supplier", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "companyName", "street", "city", "bankAccountNumber", "supplies");

        filter.setPlaceholder("Filter by supplier name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editObject(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editObject(new Supplier()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        // Initialize listing
        listCustomers(null);
    }

    // tag::listCustomers[]
    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            grid.setItems(repository.findByCompanyNameStartsWithIgnoreCase(filterText));
        }
    }
    // end::listCustomers[]

}
