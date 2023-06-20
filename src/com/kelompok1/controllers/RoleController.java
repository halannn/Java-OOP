package com.kelompok1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import com.kelompok1.models.Role;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;
import com.kelompok1.types.QueryOptions;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class RoleController extends BaseController implements Initializable {
    /* Attributes for Role Table page */
    @FXML
    private TextField searchInput;

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TableColumn<Role, String> namaRoleCol;

    @FXML
    private TableColumn<Role, String> permissionsCol;

    @FXML
    private TableColumn<Role, Void> aksiCol;

    @FXML
    private Button prevBtn;

    @FXML
    private Button nextBtn;

    private int currentPage = 1;
    private int pageTotal = 1;
    private int limitVal = 50;
    /* End of attributes for Role Table page */
    /* Attributes for Tambah/Ubah Role Form page */
    @FXML
    private TextField namaRoleInput;

    @FXML
    private GridPane permissionsGrid;

    private List<CheckBox> permissions = new ArrayList<>();

    /* End of attributes for Tambah/Ubah Role Form page */
    /* Attributes for Ubah Form Page */
    private ObjectProperty<Role> selectedRole = new SimpleObjectProperty<>();
    /* End of attributes for Ubah Form Page */

    public Role getSelectedRole() {
        return selectedRole.get();
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole.set(selectedRole);
    }

    public ObjectProperty<Role> selectedRoleProperty() {
        return selectedRole;
    }

    public void initialize(URL location, ResourceBundle resources) {

        try {
            String filename = (new File(location.toURI().toString())).getName();
            if (filename.equals("Role.fxml")) {
                namaRoleCol.setCellValueFactory(new PropertyValueFactory<Role, String>("namaRole"));
                permissionsCol.setCellValueFactory(arg0 -> {
                    return new SimpleStringProperty(
                            String.join("\n",
                                    Permissions.getPermissionsString(arg0.getValue().getPermissionsFlag())));
                });
                aksiCol.setCellFactory(new Callback<TableColumn<Role, Void>, TableCell<Role, Void>>() {
                    @Override
                    public TableCell<Role, Void> call(TableColumn<Role, Void> param) {
                        return new TableCell<Role, Void>() {
                            private final HBox actionWrapper = new HBox();

                            private final Button ubahBtn = new Button("Ubah");
                            private final Button hapusBtn = new Button("Hapus");

                            {
                                ubahBtn.getStyleClass().add("secondary-btn");
                                hapusBtn.getStyleClass().add("danger-btn");
                                actionWrapper.getChildren().addAll(ubahBtn, hapusBtn);
                                ubahBtn.setOnAction((event) -> {
                                    Role role = getTableView().getItems().get(getIndex());
                                    handleUbahBtn(role);
                                });
                                hapusBtn.setOnAction((event) -> {
                                    Role role = getTableView().getItems().get(getIndex());
                                    hapusRole(role);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(actionWrapper);
                                }
                            }
                        };
                    }
                });
                userProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                        if (newValue == null) {
                            userProperty().removeListener(this);
                        }
                        refresh();
                    }
                });
                searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    refresh();
                });
            } else if (filename.equals("TambahRole.fxml")) {
                permissionsGrid.getChildren().forEach((node) -> {
                    if (node instanceof CheckBox) {
                        permissions.add((CheckBox) node);
                    }
                });
            } else if (filename.equals("UbahRole.fxml")) {
                selectedRole.addListener(new ChangeListener<Role>() {

                    @Override
                    public void changed(ObservableValue<? extends Role> observable, Role oldValue, Role newValue) {
                        if (newValue == null) {
                            selectedRole.removeListener(this);
                            return;
                        }
                        namaRoleInput.setText(newValue.getNamaRole());

                        for (int permissionIndex = 0; permissionIndex < permissionsGrid.getChildren()
                                .size(); permissionIndex++) {
                            Node node = permissionsGrid.getChildren().get(permissionIndex);
                            if (node instanceof CheckBox) {
                                CheckBox permission = (CheckBox) node;
                                permissions.add(permission);
                                boolean isSelected = false;
                                if ((((int) Math.pow(2, permissionIndex)) & newValue.getPermissionsFlag()) > 0) {
                                    isSelected = true;
                                }
                                permission.setSelected(isSelected);
                            }
                        }
                        ;
                    }

                });

            }
        } catch (

        URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        if (currentPage == 0) {
            currentPage = 1;
        }
        QueryOptions options = new QueryOptions();
        options.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        options.setLimit(OptionalInt.of(limitVal));
        if (searchInput.getText().length() > 0) {
            options.setSearch(Optional.of(searchInput.getText()));
        }
        options.setCurrentPage(OptionalInt.of(currentPage));
        int itemTotal = Role.getAllCount(options);
        pageTotal = (int) Math.ceil((double) itemTotal / limitVal);
        if (itemTotal == 0) {
            currentPage = 0;
            pageTotal = 0;
        }
        ObservableList<Role> data = Role.getAll(options);
        roleTable.setItems(data);

        prevBtn.setDisable(false);
        nextBtn.setDisable(false);

        if (currentPage <= 1) {
            prevBtn.setDisable(true);
        }

        if (currentPage >= pageTotal) {
            nextBtn.setDisable(true);
        }
    }

    public void handleUbahBtn(Role selectedRole) {
        try {
            RoleController controller = (RoleController) switchView("./views/UbahRole.fxml");
            controller.setSelectedRole(selectedRole);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prevPage(ActionEvent event) {
        currentPage--;
        refresh();
    }

    public void nextPage(ActionEvent event) {
        currentPage++;
        refresh();
    }

    public void tambahRole(ActionEvent event) {
        if (namaRoleInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Nama Role tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        Role role = new Role(namaRoleInput.getText(), 0);
        int permissionIndex = 0;
        int permissionsFlag = 0;
        for (CheckBox permission : permissions) {
            int isPermitted = 0;
            if (permission.isSelected()) {
                isPermitted = 1;
                permissionsFlag = permissionsFlag | (((int) Math.pow(2, permissionIndex)) * isPermitted);
            }
            permissionIndex++;
        }
        ;
        role.setPermissionsFlag(permissionsFlag);
        role.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        role.tambah();

        try {
            switchView("./views/Role.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ubahRole(ActionEvent event) {
        String namaRole = namaRoleInput.getText();
        int permissionIndex = 0;
        int permissionsFlag = 0;
        for (CheckBox permission : permissions) {
            int isPermitted = 0;
            if (permission.isSelected()) {
                isPermitted = 1;
                permissionsFlag = permissionsFlag | (((int) Math.pow(2, permissionIndex)) * isPermitted);
            }
            permissionIndex++;
        }
        ;
        if (namaRole.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Nama role tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        selectedRole.get().setNamaRole(namaRole);
        selectedRole.get().setPermissionsFlag(permissionsFlag);
        selectedRole.get().ubah();

        try {
            switchView("./views/Role.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusRole(Role selectedRole) {
        selectedRole.hapus();
        refresh();
    }
}
