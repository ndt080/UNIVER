namespace magma
{
    internal class MagmaE32 : Converter
    {
        private readonly uint[] _key;
        private readonly ulong[] _data;
        private const uint BlockSize = 8;
        public byte[] GetEncryptData { get; }

        public MagmaE32(byte[] data, byte[] key)
        {
            _key = GetUIntKeyArray(key);
            _data = GetULongDataArray(data);
            GetEncryptData = ConvertToByte(EncryptFile());
        }

        private ulong[] EncryptFile()
        {
            var basicStep = new BasicStep[BlockSize];
            var encryptData = new ulong[_data.Length];

            for (var z = 0; z < _data.Length; z++)
            {
                encryptData[z] = _data[z];
                for (var j = 0; j < 3; j++)
                {
                    for (var i = 0; i < basicStep.Length; i++)
                    {
                        basicStep[i] = new BasicStep(encryptData[z], _key[i]);
                        encryptData[z] = basicStep[i].BasicEncrypt(false);
                    }
                }
                for (var i = basicStep.Length - 1; i >= 0; i--)
                {
                    basicStep[i] = new BasicStep(encryptData[z], _key[i]);
                    encryptData[z] = basicStep[i].BasicEncrypt((i == 0));
                }
            }

            return encryptData;
        }
    }
}