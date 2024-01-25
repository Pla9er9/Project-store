function validateProjectName(name: string): string {
	name = name.trim();
	if (name.length < 1 || name.length > 20) {
		return 'Length should be beetwen 1 and 30 characters';
	}
	return '';
}

export { validateProjectName };
