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
            this.componentTip = new System.Windows.Forms.ToolTip(this.components);
            this.hwTextBox = new System.Windows.Forms.TextBox();
            this.groupBoxId = new System.Windows.Forms.GroupBox();
            this.copyButton = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBoxId.SuspendLayout();
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
            this.groupBox1.Location = new System.Drawing.Point(29, 83);
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
            this.groupBox2.Location = new System.Drawing.Point(29, 152);
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
            this.label1.Location = new System.Drawing.Point(29, 22);
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
            this.groupBox3.Location = new System.Drawing.Point(29, 221);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(461, 63);
            this.groupBox3.TabIndex = 2;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Sélectionner votre logiciel :";
            // 
            // submit
            // 
            this.submit.Location = new System.Drawing.Point(396, 388);
            this.submit.Name = "submit";
            this.submit.Size = new System.Drawing.Size(94, 29);
            this.submit.TabIndex = 3;
            this.submit.Text = "Valider";
            this.submit.UseVisualStyleBackColor = true;
            this.submit.Click += new System.EventHandler(this.submit_Click);
            // 
            // componentTip
            // 
            this.componentTip.ToolTipIcon = System.Windows.Forms.ToolTipIcon.Info;
            // 
            // hwTextBox
            // 
            this.hwTextBox.Location = new System.Drawing.Point(6, 26);
            this.hwTextBox.Name = "hwTextBox";
            this.hwTextBox.PlaceholderText = "Echec de la récupération de l\'identifiant hardware";
            this.hwTextBox.ReadOnly = true;
            this.hwTextBox.Size = new System.Drawing.Size(382, 27);
            this.hwTextBox.TabIndex = 1;
            this.componentTip.SetToolTip(this.hwTextBox, "Si le logiciel ne parvient pas à se connecter au serveur pour\r\nrécupérer la licen" +
        "ce, vous pouvez envoyer cet identifiant\r\nà l\'administrateur afin d\'obtenir la li" +
        "cence.");
            // 
            // groupBoxId
            // 
            this.groupBoxId.Controls.Add(this.copyButton);
            this.groupBoxId.Controls.Add(this.hwTextBox);
            this.groupBoxId.Location = new System.Drawing.Point(29, 302);
            this.groupBoxId.Name = "groupBoxId";
            this.groupBoxId.Size = new System.Drawing.Size(461, 63);
            this.groupBoxId.TabIndex = 2;
            this.groupBoxId.TabStop = false;
            this.groupBoxId.Text = "Identifiant hardware";
            // 
            // copyButton
            // 
            this.copyButton.Location = new System.Drawing.Point(394, 26);
            this.copyButton.Name = "copyButton";
            this.copyButton.Size = new System.Drawing.Size(61, 27);
            this.copyButton.TabIndex = 4;
            this.copyButton.Text = "Copier";
            this.copyButton.UseVisualStyleBackColor = true;
            this.copyButton.Click += new System.EventHandler(this.copyButton_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(522, 443);
            this.Controls.Add(this.groupBoxId);
            this.Controls.Add(this.submit);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.MinimumSize = new System.Drawing.Size(540, 490);
            this.Name = "Form1";
            this.Text = "Logiciel de récupération de licence";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBoxId.ResumeLayout(false);
            this.groupBoxId.PerformLayout();
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
        private ToolTip componentTip;
        private GroupBox groupBoxId;
        private TextBox hwTextBox;
        private Button copyButton;
    }
}