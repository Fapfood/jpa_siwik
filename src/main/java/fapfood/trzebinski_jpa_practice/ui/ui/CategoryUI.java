package fapfood.trzebinski_jpa_practice.ui.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import fapfood.trzebinski_jpa_practice.model.Category;
import fapfood.trzebinski_jpa_practice.model.Supplier;
import fapfood.trzebinski_jpa_practice.repository.CategoryRepository;
import fapfood.trzebinski_jpa_practice.repository.SupplierRepository;
import fapfood.trzebinski_jpa_practice.ui.editor.CategoryEditor;
import fapfood.trzebinski_jpa_practice.ui.editor.SupplierEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI(path = "/category")
public class CategoryUI extends UI {

    private final CategoryRepository repository;

    private final CategoryEditor editor;

    final Grid<Category> grid;

    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public CategoryUI(CategoryRepository repository, CategoryEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Category.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New category", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "categoryName", "products");

        filter.setPlaceholder("Filter by category name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editObject(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editObject(new Category()));

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
            grid.setItems(repository.findByCategoryNameStartsWithIgnoreCase(filterText));
        }
    }
    // end::listCustomers[]

}
