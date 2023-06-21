package com.kelompok1.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.ResourceBundle;

import com.kelompok1.models.Role;
import com.kelompok1.models.User;
import com.kelompok1.types.Permissions;
import com.kelompok1.types.QueryOptions;
import com.kelompok1.types.UserStatus;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class UserController extends BaseController {

    @FXML
    private BaseController sidebarController;

    /* Attributes for Role Table page */
    @FXML
    private TextField searchInput;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, Role> roleCol;

    @FXML
    private TableColumn<User, String> statusCol;

    @FXML
    private TableColumn<User, Void> aksiCol;

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
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private ComboBox<Role> roleInput;

    /* End of attributes for Tambah/Ubah Role Form page */
    /* Attributes for Ubah Form Page */
    @FXML
    private ComboBox<UserStatus> statusInput;

    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();
    /* End of attributes for Ubah Form Page */

    public User getSelectedUser() {
        return selectedUser.get();
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }

    public ObjectProperty<User> selectedUserProperty() {
        return selectedUser;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        try {
            String filename = (new File(location.toURI().toString())).getName();
            if (filename.equals("User.fxml")) {
                usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
                roleCol.setCellValueFactory(arg0 -> {
                    return new SimpleObjectProperty<Role>(arg0.getValue().role());
                });
                roleCol.setCellFactory(arg0 -> {
                    return new TableCell<User, Role>() {
                        @Override
                        protected void updateItem(Role item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText("");
                            }else{
                                setText(item.getNamaRole());
                            }
                        }
                    };
                });
                statusCol.setCellValueFactory(arg0 -> {
                    return new SimpleStringProperty(arg0.getValue().getStatus().toString());
                });
                aksiCol.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
                    @Override
                    public TableCell<User, Void> call(TableColumn<User, Void> param) {
                        return new TableCell<User, Void>() {
                            private final HBox actionWrapper = new HBox();

                            private final Button ubahBtn = new Button("Ubah");
                            private final Button hapusBtn = new Button("Hapus");

                            {
                                ubahBtn.getStyleClass().add("secondary-btn");
                                hapusBtn.getStyleClass().add("danger-btn");
                                actionWrapper.getChildren().addAll(ubahBtn, hapusBtn);
                                ubahBtn.setOnAction((event) -> {
                                    User user = getTableView().getItems().get(getIndex());
                                    handleUbahBtn(user);
                                });
                                hapusBtn.setOnAction((event) -> {
                                    User user = getTableView().getItems().get(getIndex());
                                    hapusUser(user);
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                }else{
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
            } else if (filename.equals("TambahUser.fxml")) {
                userProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                        if (newValue == null) {
                            userProperty().removeListener(this);
                        }
                        QueryOptions options = new QueryOptions();
                        options.setLimit(OptionalInt.of(Integer.MAX_VALUE));
                        options.setCurrentPage(OptionalInt.of(1));
                        options.setIdOwnPerusahaan(newValue.getIdOwnPerusahaan());
                        ObservableList<Role> roles = Role.getAll(options);
                        roleInput.setItems(roles);
                        roleInput.setConverter(new StringConverter<Role>() {
                            @Override
                            public String toString(Role role) {
                                return role.getNamaRole();
                            }

                            @Override
                            public Role fromString(String string) {
                                return null;
                            }
                        });
                    }
                });
            } else if (filename.equals("UbahUser.fxml")) {
                
                selectedUserProperty().addListener(new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                        if (newValue == null) {
                            userProperty().removeListener(this);
                        }

                        usernameInput.setText(newValue.getUsername());
                        passwordInput.setText("");

                        QueryOptions options = new QueryOptions();
                        options.setLimit(OptionalInt.of(Integer.MAX_VALUE));
                        options.setCurrentPage(OptionalInt.of(1));
                        options.setIdOwnPerusahaan(newValue.getIdOwnPerusahaan());
                        ObservableList<Role> roles = Role.getAll(options);
                        roleInput.setItems(roles);
                        roleInput.setConverter(new StringConverter<Role>() {
                            @Override
                            public String toString(Role role) {
                                return role.getNamaRole();
                            }

                            @Override
                            public Role fromString(String string) {
                                return null;
                            }
                        });

                        statusInput.getItems().add(UserStatus.Aktif);
                        statusInput.getItems().add(UserStatus.Nonaktif);

                        for (Role item : roleInput.getItems()) {
                            if(item.getId()==newValue.role().getId()){
                                roleInput.setValue(item);
                            }
                        }
                        statusInput.setValue(newValue.getStatus());
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
        int itemTotal = User.getAllCount(options);
        pageTotal = (int) Math.ceil((double) itemTotal / limitVal);
        if (itemTotal == 0) {
            currentPage = 0;
            pageTotal = 0;
        }
        ObservableList<User> data = User.getAll(options);
        userTable.setItems(data);

        prevBtn.setDisable(false);
        nextBtn.setDisable(false);

        if (currentPage <= 1) {
            prevBtn.setDisable(true);
        }

        if (currentPage >= pageTotal) {
            nextBtn.setDisable(true);
        }
    }

    public void handleUbahBtn(User selectedUser) {
        try {
            UserController controller = (UserController) switchView("./views/UbahUser.fxml");
            controller.setSelectedUser(selectedUser);
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

    public void tambahUser(ActionEvent event) {
        if (usernameInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Username tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (passwordInput.getText().equals("")) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Password tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        if (roleInput.getValue() == null) {
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Role tidak boleh kosong");
            alt.showAndWait();
            return;
        }
        User user = new User();
        user.setUsername(usernameInput.getText());
        String hashedPassword = User.hashPassword(passwordInput.getText());
        user.setHashedPassword(hashedPassword);
        user.setIdRole(roleInput.getValue().getId());
        user.setIdOwnPerusahaan(getUser().getIdOwnPerusahaan());
        user.setStatus(UserStatus.Aktif);
        user.tambah();

        try {
            switchView("./views/User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ubahUser(ActionEvent event) {
        String username = usernameInput.getText();
        if (username.equals("")) {
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setContentText("Username tidak boleh kosong");
            alt.showAndWait();
            return;
        }

        selectedUser.get().setUsername(username);
        if (!passwordInput.getText().equals("")) {
            String hashedPassword = User.hashPassword(passwordInput.getText());
            selectedUser.get().setHashedPassword(hashedPassword);
        }
        selectedUser.get().setIdRole(roleInput.getValue().getId());
        selectedUser.get().setStatus(statusInput.getValue());
        selectedUser.get().ubah();

        try {
            switchView("./views/User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hapusUser(User selectedUser) {
        if((getUser().role().getPermissionsFlag() & Permissions.MENGHAPUS_USER) == 0){
            Alert alt = new Alert(AlertType.ERROR);
            alt.setContentText("Anda tidak memiliki akses untuk menghapus user");
            alt.showAndWait();
            return;
        }
        selectedUser.hapus();
        refresh();
    }
}
