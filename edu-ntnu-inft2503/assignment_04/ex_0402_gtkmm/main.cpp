#include <gtkmm.h>

class Window : public Gtk::Window {
public:
  Gtk::Box box;
  Gtk::Entry entry_fname;
  Gtk::Entry entry_lname;
  Gtk::Button button;
  Gtk::Label label_fname;
  Gtk::Label label_lname;
  Gtk::Label label_show_result;
  Gtk::Label label_info;

  Window() : box(Gtk::Orientation::ORIENTATION_VERTICAL) {
    set_title("Weyland-Yutani Name Combinatorizer X2000");
    button.set_label("Builder Button(TM)");

    button.set_sensitive(false); // Start the button as inactive

    label_fname.set_label("First name");
    box.pack_start(label_fname);  // Add the widget label to box
    box.pack_start(entry_fname);  // Add the widget entry to box

    label_lname.set_label("Last name");
    box.pack_start(label_lname);  // Add the widget label to box
    box.pack_start(entry_lname);  // Add the widget entry to box

    box.pack_start(button); // Add the widget button to box

    label_info.set_label("Combinatorized name:");
    box.pack_start(label_info);  // Add the widget label to box
    box.pack_start(label_show_result);  // Add the widget label to box

    add(box);   // Add vbox to window
    show_all(); // Show all widgets


    auto update_button_state = [this]() {
      bool enable_button = !entry_fname.get_text().empty() && !entry_lname.get_text().empty();
      button.set_sensitive(enable_button);
    };

    entry_fname.signal_changed().connect(update_button_state);
    entry_lname.signal_changed().connect(update_button_state);

    button.signal_clicked().connect([this]() {
      label_show_result.set_text(entry_fname.get_text() + " " + entry_lname.get_text());
    });
  }
};

int main() {
  auto app = Gtk::Application::create();
  Window window;
  return app->run(window);
}
