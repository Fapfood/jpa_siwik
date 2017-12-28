package fapfood.trzebinski_jpa_practice.ui.editor;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TextField;
import fapfood.trzebinski_jpa_practice.model.Category;
import fapfood.trzebinski_jpa_practice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CategoryEditor extends AbstractEditor {

    @Autowired
    private CategoryRepository repository;

    private Category object;

    TextField categoryName = new TextField("category name");

    Binder<Category> binder = new Binder<>(Category.class);

    public CategoryEditor() {
        super();

        addComponents(categoryName, actions);

        binder.bindInstanceFields(this);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(object));
        delete.addClickListener(e -> repository.delete(object));
        cancel.addClickListener(e -> editObject(object));
        setVisible(false);
    }

    public final void editObject(Category o) {
        if (o == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = o.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            object = repository.findOne(o.getId());
        } else {
            object = o;
        }
        cancel.setVisible(persisted);

        // Bind object properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(object);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        categoryName.selectAll();
    }
}
