namespace WinFormsApp1
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.idBox = new System.Windows.Forms.TextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.passwdBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.softsComboBox = new System.Windows.Forms.ComboBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.submit = new System.Windows.Forms.Button();
            this.componentBox = new System.Windows.Forms.GroupBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.checkBoxProc = new System.Windows.Forms.CheckBox();
            this.checkBoxBaseBoard = new System.Windows.Forms.CheckBox();
            this.checkBoxBIOS = new System.Windows.Forms.CheckBox();
            this.checkBoxMAC = new System.Windows.Forms.CheckBox();
            this.checkBoxDiskDrive = new System.Windows.Forms.CheckBox();
            this.componentTip = new System.Windows.Forms.ToolTip(this.components);
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.componentBox.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 36.46907F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel2.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 2;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(200, 100);
            this.tableLayoutPanel2.TabIndex = 0;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.idBox);
            this.groupBox1.Location = new System.Drawing.Point(29, 74);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(461, 63);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Identifiant (adresse mail)";
            // 
            // idBox
            // 
            this.idBox.Location = new System.Drawing.Point(6, 26);
            this.idBox.Name = "idBox";
            this.idBox.PlaceholderText = "Entrez votre adresse email";
            this.idBox.Size = new System.Drawing.Size(449, 27);
            this.idBox.TabIndex = 0;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.passwdBox);
            this.groupBox2.Location = new System.Drawing.Point(29, 143);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(461, 63);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Mot de passe";
            // 
            // passwdBox
            // 
            this.passwdBox.Location = new System.Drawing.Point(6, 26);
            this.passwdBox.Name = "passwdBox";
            this.passwdBox.PasswordChar = '●';
            this.passwdBox.PlaceholderText = "Entrez votre mot de passe";
            this.passwdBox.Size = new System.Drawing.Size(449, 27);
            this.passwdBox.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(29, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(467, 40);
            this.label1.TabIndex = 2;
            this.label1.Text = "Afin de récupérer votre licence, veuillez renseigner vos identifiants de\nconnexio" +
    "n, ainsi que le logiciel souhaité puis cliquez sur \'Valider\'.";
            // 
            // softsComboBox
            // 
            this.softsComboBox.FormattingEnabled = true;
            this.softsComboBox.Location = new System.Drawing.Point(6, 26);
            this.softsComboBox.Name = "softsComboBox";
            this.softsComboBox.Size = new System.Drawing.Size(449, 28);
            this.softsComboBox.TabIndex = 0;
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.softsComboBox);
            this.groupBox3.Location = new System.Drawing.Point(29, 212);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(461, 63);
            this.groupBox3.TabIndex = 2;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Sélectionner votre logiciel :";
            // 
            // submit
            // 
            this.submit.Location = new System.Drawing.Point(396, 424);
            this.submit.Name = "submit";
            this.submit.Size = new System.Drawing.Size(94, 29);
            this.submit.TabIndex = 3;
            this.submit.Text = "Valider";
            this.submit.UseVisualStyleBackColor = true;
            this.submit.Click += new System.EventHandler(this.submit_Click);
            // 
            // componentBox
            // 
            this.componentBox.Controls.Add(this.tableLayoutPanel1);
            this.componentBox.Location = new System.Drawing.Point(29, 281);
            this.componentBox.Name = "componentBox";
            this.componentBox.Size = new System.Drawing.Size(461, 137);
            this.componentBox.TabIndex = 4;
            this.componentBox.TabStop = false;
            this.componentBox.Text = "Composants utilisés pour la génération de licence";
            this.componentTip.SetToolTip(this.componentBox, "Ces composants seront utilisés pour générer un identifiant unique correspondant à" +
        "\nvotre machine. Si vous ne savez pas quoi choisir conserver le choix par défaut." +
        "");
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 49.97221F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50.02779F));
            this.tableLayoutPanel1.Controls.Add(this.checkBoxProc, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.checkBoxBaseBoard, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.checkBoxBIOS, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.checkBoxMAC, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.checkBoxDiskDrive, 0, 1);
            this.tableLayoutPanel1.Location = new System.Drawing.Point(6, 26);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.Padding = new System.Windows.Forms.Padding(2);
            this.tableLayoutPanel1.RowCount = 3;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33333F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33334F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 33.33334F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(449, 105);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // checkBoxProc
            // 
            this.checkBoxProc.AutoSize = true;
            this.checkBoxProc.Location = new System.Drawing.Point(5, 71);
            this.checkBoxProc.Name = "checkBoxProc";
            this.checkBoxProc.Size = new System.Drawing.Size(101, 24);
            this.checkBoxProc.TabIndex = 9;
            this.checkBoxProc.Tag = "Processor";
            this.checkBoxProc.Text = "Processeur";
            this.checkBoxProc.UseVisualStyleBackColor = true;
            // 
            // checkBoxBaseBoard
            // 
            this.checkBoxBaseBoard.AutoSize = true;
            this.checkBoxBaseBoard.Checked = true;
            this.checkBoxBaseBoard.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxBaseBoard.Location = new System.Drawing.Point(227, 5);
            this.checkBoxBaseBoard.Name = "checkBoxBaseBoard";
            this.checkBoxBaseBoard.Size = new System.Drawing.Size(104, 24);
            this.checkBoxBaseBoard.TabIndex = 6;
            this.checkBoxBaseBoard.Tag = "BaseBoard";
            this.checkBoxBaseBoard.Text = "Carte mère";
            this.checkBoxBaseBoard.UseVisualStyleBackColor = true;
            // 
            // checkBoxBIOS
            // 
            this.checkBoxBIOS.AutoSize = true;
            this.checkBoxBIOS.Checked = true;
            this.checkBoxBIOS.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxBIOS.Location = new System.Drawing.Point(227, 38);
            this.checkBoxBIOS.Name = "checkBoxBIOS";
            this.checkBoxBIOS.Size = new System.Drawing.Size(63, 24);
            this.checkBoxBIOS.TabIndex = 7;
            this.checkBoxBIOS.Tag = "BIOS";
            this.checkBoxBIOS.Text = "BIOS";
            this.checkBoxBIOS.UseVisualStyleBackColor = true;
            // 
            // checkBoxMAC
            // 
            this.checkBoxMAC.AutoSize = true;
            this.checkBoxMAC.Checked = true;
            this.checkBoxMAC.CheckState = System.Windows.Forms.CheckState.Checked;
            this.checkBoxMAC.Location = new System.Drawing.Point(5, 5);
            this.checkBoxMAC.Name = "checkBoxMAC";
            this.checkBoxMAC.Size = new System.Drawing.Size(159, 24);
            this.checkBoxMAC.TabIndex = 5;
            this.checkBoxMAC.Tag = "MAC";
            this.checkBoxMAC.Text = "Carte réseau (MAC)";
            this.checkBoxMAC.UseVisualStyleBackColor = true;
            // 
            // checkBoxDiskDrive
            // 
            this.checkBoxDiskDrive.AutoSize = true;
            this.checkBoxDiskDrive.Location = new System.Drawing.Point(5, 38);
            this.checkBoxDiskDrive.Name = "checkBoxDiskDrive";
            this.checkBoxDiskDrive.Size = new System.Drawing.Size(103, 24);
            this.checkBoxDiskDrive.TabIndex = 8;
            this.checkBoxDiskDrive.Tag = "HardDrive";
            this.checkBoxDiskDrive.Text = "Disque dur";
            this.checkBoxDiskDrive.UseVisualStyleBackColor = true;
            // 
            // componentTip
            // 
            this.componentTip.ToolTipIcon = System.Windows.Forms.ToolTipIcon.Info;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(522, 473);
            this.Controls.Add(this.componentBox);
            this.Controls.Add(this.submit);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.MinimumSize = new System.Drawing.Size(540, 520);
            this.Name = "Form1";
            this.Text = "Logiciel de récupération de licence";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.componentBox.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private TableLayoutPanel tableLayoutPanel2;
        private GroupBox groupBox1;
        private GroupBox groupBox2;
        private TextBox idBox;
        private TextBox passwdBox;
        private Label label1;
        private ComboBox softsComboBox;
        private GroupBox groupBox3;
        private Button submit;
        private GroupBox componentBox;
        private TableLayoutPanel tableLayoutPanel1;
        private ToolTip componentTip;
        private CheckBox checkBoxProc;
        private CheckBox checkBoxDiskDrive;
        private CheckBox checkBoxBIOS;
        private CheckBox checkBoxBaseBoard;
        private CheckBox checkBoxMAC;
    }
}