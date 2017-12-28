package fapfood.trzebinski_jpa_practice.ui.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import fapfood.trzebinski_jpa_practice.model.BusinessTransaction;
import fapfood.trzebinski_jpa_practice.model.Category;
import fapfood.trzebinski_jpa_practice.model.Supplier;
import fapfood.trzebinski_jpa_practice.repository.BusinessTransactionRepository;
import fapfood.trzebinski_jpa_practice.repository.CategoryRepository;
import fapfood.trzebinski_jpa_practice.ui.editor.CategoryEditor;
import fapfood.trzebinski_jpa_practice.ui.editor.TransactionEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@SpringUI(path = "/transaction")
public class TransactionUI extends UI {

    private final BusinessTransactionRepository repository;

    private final TransactionEditor editor;

    final Grid<BusinessTransaction> grid;

    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public TransactionUI(BusinessTransactionRepository repository, TransactionEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(BusinessTransaction.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New transaction", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "date", "sales");

        filter.setPlaceholder("Filter by date");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editObject(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editObject(new BusinessTransaction()));

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
            DateFormat format = new SimpleDateFormat("DD MM YYYY", Locale.ENGLISH);
            try {
                Date date = format.parse(filterText);
                grid.setItems(repository.findByDate(date));
            } catch (ParseException e) {
                grid.setItems(repository.findAll());
            }
        }
    }
    // end::listCustomers[]

}
