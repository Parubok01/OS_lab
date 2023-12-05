Name:           calcfiles
Version:        1.0
Release:        1%{?dist}
Summary:        A simple script to calculate files in a directory
Requires:       unzip

License:        MIT
URL:            https://github.com/Parubok01/OS_lab
Source0:        https://github.com/Parubok01/OS_lab/archive/main.zip

BuildArch:      noarch

%description
calc_files.sh is a simple script that calculates the number of files in a directory.

%prep
unzip %SOURCE0
cd OS_lab-main/

%install
mkdir -p %{buildroot}/usr/bin
install -m 755 %{_builddir}/OS_lab-main/calc_files.sh %{buildroot}/usr/bin/calc_files

%files
/usr/bin/calc_files

%changelog
* Tue Dec 05 2023 Bogdan Korh <bogdan.korh.285@gmail.com> - 1.0-1
- Initial build