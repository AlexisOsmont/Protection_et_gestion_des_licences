using System;
using System.Collections.Generic;
using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Xml;
using System.Windows.Markup;

namespace ProjetAnnuel
{
    /// <summary>
    /// Logique d'interaction pour MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private static UIElement skelElement;
        private static bool deleteMode = false;
        public MainWindow()
        {
            Project.init();
            InitializeComponent();
        }

        private void wndMain_Loaded(object sender, RoutedEventArgs e)
        {
            initList();

            if (Project.connectionUsername != null)
            {
                lblConnectedAs.Content = "Connected as : " + Project.connectionUsername;
            }
            else
            {
                lblConnectedAs.Content = "Not Connected !";
            }

            if (Project.licenceLoadSuccessfull)
            {
                foreach (Licence el in Project.licences)
                {
                    addToList(el.name, el.period);
                }
            }

        }

        private void btnAddLicence_Click(object sender, RoutedEventArgs e)
        {
            Project.addedLicenceName = null;

            bool ok = false;
            AddLicenceWindow addLicence = new AddLicenceWindow();
            ok = (bool)addLicence.ShowDialog();

            if (Project.addedLicenceName != null)
            {
                if (Project.tryAddLicence())
                {
                    MessageBox.Show("Your licence has been successfully activated !", "Activation software", MessageBoxButton.OK, MessageBoxImage.Information);
                    addToList(Project.addedLicenceName, Project.addedLicencePeriod);
                }
                else
                {
                    MessageBox.Show("OOpps ! Registration of this programme failed please verify its name or the fact that you own this programm",
                        "Activation software", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                }
            }
            else
            {
                MessageBox.Show("No program name was entered",
                    "Activation software", MessageBoxButton.OK, MessageBoxImage.Exclamation);
            }
        }

        private void btnDelLicence_Click(object sender, RoutedEventArgs e)
        {
            bool sure = false;
            deleteMode = !deleteMode;
            if (deleteMode)
            {
                setUIdeleteMode(true);
                btnDelLicence.Content = "Ok ! Delete all these Licence(s)";
            }
            else
            {
                List<UIElement> toRemove = new List<UIElement>();
                foreach (UIElement el in stckpList.Children)
                {
                    if (getUiitemCheckBox(el))
                    {
                        toRemove.Add(el);
                    }
                }
                if (toRemove.Count > 0)
                {
                    MessageBoxResult res = MessageBox.Show("Are you sure ? You are deleting some licences ! You might loose data permanently",
                        "Activation software", MessageBoxButton.OKCancel, MessageBoxImage.Warning);

                    if (res == MessageBoxResult.OK)
                    {
                        sure = true;
                    }
                    else
                    {
                        foreach (UIElement el in toRemove)
                        {
                            setUiitemCheckBoxState(el, false);
                        }
                    }
                }
                if (sure)
                {
                    foreach (UIElement el in toRemove)
                    {
                        stckpList.Children.Remove(el);
                        Project.deleteLicence(getUIitemName(el));
                    }
                }
                setUIdeleteMode(false);
                btnDelLicence.Content = "Delete licence";
            }
        }



        //management of the UI List
        private void initList()
        {
            foreach (UIElement el in stckpList.Children)
            {
                skelElement = el;
            }
            stckpList.Children.Remove(skelElement);
        }

        private void addToList(String elementName, String elementPeriod)
        {
            //clone the skel UI element
            string gridXaml = XamlWriter.Save(skelElement);
            StringReader stringReader = new StringReader(gridXaml);
            XmlReader xmlReader = System.Xml.XmlReader.Create(stringReader);
            UIElement newElement = (Grid)XamlReader.Load(xmlReader);

            setUIitemName(newElement, elementName);
            setUIitemPeriod(newElement, "remaining : " + elementPeriod + " days");
            setUiitemCheckBox(newElement, false);

            stckpList.Children.Add(newElement);
        }

        private void setUIdeleteMode(bool enabled)
        {
            foreach (UIElement el in stckpList.Children)
            {
                setUiitemCheckBox(el, enabled);
            }
        }

        private void setUIitemName(UIElement el, String name)
        {
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                //search the inner grid 2 times
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner textbox name
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Label) && ((Label)element).Name == "lblFirstItem")
                        {
                            //found the inner name textbox
                            ((Label)element).Content = name;
                        }
                    }
                }
            }
        }

        private String getUIitemName(UIElement el)
        {
            String result = null;
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                Label lbName = null;
                //search the inner grid
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner textbox name
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Label) && ((Label)element).Name == "lblFirstItem")
                        {
                            //found the inner name textbox
                            lbName = (Label)element;
                            result = (string)lbName.Content;
                        }
                    }
                }
            }
            return result;
        }

        private void setUIitemPeriod(UIElement el, String period)
        {
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                //search the inner grid
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner textbox period
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Label) && ((Label)element).Name == "lblFirstItemRemaining")
                        {
                            //found the inner period textbox
                            ((Label)element).Content = period;
                        }
                    }
                }
            }
        }

        private void setUiitemCheckBox(UIElement el, bool enabled)
        {
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                CheckBox checkBox = null;
                //search the inner grid
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner checkBox
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(CheckBox))
                        {
                            //found the checkBox
                            checkBox = (CheckBox)element;
                            checkBox.Visibility = enabled ? Visibility.Visible : Visibility.Hidden;
                        }
                    }
                }
            }
        }
        private void setUiitemCheckBoxState(UIElement el, bool enabled)
        {
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                //search the inner grid
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner checkBox
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(CheckBox))
                        {
                            //found the checkBox
                            ((CheckBox)element).IsChecked = enabled;
                        }
                    }
                }
            }
        }
        private bool getUiitemCheckBox(UIElement el)
        {
            bool result = false;
            if (el != null && el.GetType() == typeof(Grid))
            {
                Grid grid = null;
                //search the inner grid
                foreach (UIElement element in ((Grid)el).Children)
                {
                    if (element.GetType() == typeof(Grid))
                    {
                        //found the inner grid
                        grid = (Grid)element;
                    }
                }
                if (grid != null)
                {
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(Grid))
                        {
                            //found the inner grid
                            grid = (Grid)element;
                        }
                    }
                }
                if (grid != null)
                {
                    //search the inner checkBox
                    foreach (UIElement element in grid.Children)
                    {
                        if (element.GetType() == typeof(CheckBox))
                        {
                            //found the checkBox
                            result = (bool)((CheckBox)element).IsChecked;
                        }
                    }
                }
            }
            return result;
        }
    }
}
